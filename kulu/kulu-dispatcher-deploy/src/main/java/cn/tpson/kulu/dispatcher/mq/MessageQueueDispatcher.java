package cn.tpson.kulu.dispatcher.mq;

import cn.tpson.kulu.common.logger.Logger;
import cn.tpson.kulu.common.logger.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MessageQueueDispatcher implements ApplicationContextAware {
	private static final Logger log = LoggerFactory.getLogger(MessageQueueDispatcher.class);
	private static final int WORKER_NUM = 5;

    @Value("${mq.timeout}")
    private Long mqTimeout;
	private ExecutorService boss = Executors.newCachedThreadPool();
	private ExecutorService worker = Executors.newFixedThreadPool(WORKER_NUM);
	
	//一个队列对应一个Listener列表.
	private Map<String, BlockingQueue<MessageQueueListener>> queueMap = new ConcurrentHashMap<>();
	private ReentrantLock lock = new ReentrantLock();
	private AtomicInteger listeners = new AtomicInteger();
	
	@Autowired
	private MessageQueueService messageQueueService;
	
	/**
	 * 添加监听器.
	 *
	 * @param listener
	 */
	public boolean addListener(MessageQueueListener listener) {
		if (listener == null || StringUtils.isBlank(listener.getQueueName())) {
			return false;
		}
		
		String queueName = listener.getQueueName();
		boolean success;
		BlockingQueue<MessageQueueListener> queue = queueMap.get(queueName);
		
		if (queue == null) {
			final ReentrantLock rlock = this.lock;
			rlock.lock();
			try {
				queue = queueMap.get(queueName);
				if (queue == null) {
					queue = new LinkedBlockingQueue<>();
					success = queue.offer(listener);
					queueMap.put(queueName, queue);
					boss.execute(new BossHandler(queue, queueName));
				} else {
					success = queue.offer(listener);
				}
			} finally {
				rlock.unlock();
			}
		} else {
			success = queue.offer(listener);
		}
		
		if (success) {
			listeners.incrementAndGet();
		}
		
		return success;
	}
	
	/**
	 * 监听器数量.
	 *
	 * @return
	 */
	public int size() {
		return listeners.get();
	}
	
	/**
	 * 自动注册所有消息监听器.
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Map<String, MessageQueueListener> beansMap = applicationContext.getBeansOfType(MessageQueueListener.class);
		Set<Entry<String, MessageQueueListener>> entrySet = beansMap.entrySet();
		
		for (Entry<String, MessageQueueListener> entry : entrySet) {
			MessageQueueListener listener = entry.getValue();
			this.addListener(listener);
		}
	}

	@PreDestroy
	public void destory() {
		boss.shutdown();
		worker.shutdown();
	}
	
	/////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////

	/**
	 * 分发线程.
	 */
	class BossHandler implements Runnable {
		private BlockingQueue<MessageQueueListener> queue;
		private String queueName;
		public BossHandler(BlockingQueue<MessageQueueListener> queue, String queueName) {
			this.queue = queue;
			this.queueName = queueName;
		}
		
		@Override
		public void run() {
			for (;;) {
				try {
					String message = messageQueueService.bpop(queueName, mqTimeout, TimeUnit.MILLISECONDS);
					if (message != null) {
                        log.info("{}:{}", queueName, message);
						for (MessageQueueListener listener : queue) {
							worker.execute(new WorkHandler(message, listener));
						}
					}
				} catch (Exception e) {
                    log.error("分发消息出错", e);
				}
			}
		}
	}

	/**
	 * 监听器回调线程.
	 */
	class WorkHandler implements Runnable {
		private String message;
		private MessageQueueListener listener;
		public WorkHandler(String message, MessageQueueListener listener) {
			this.message = message;
			this.listener = listener;
		}
		
		@Override
		public void run() {
			listener.onMessage(message);
		}
	}
}
