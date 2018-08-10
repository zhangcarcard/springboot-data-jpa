package cn.tpson.kulu.dispatcher.mq;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 消息队列.
 */
public interface MessageQueueService {
	/**
	 * 往消息队列中放入消息.
	 *
	 * @param queueName
	 * @param message
	 */
	void push(String queueName, String message);
	
	/**
	 * 批量往消息队列中放入消息.
	 *
	 * @param queueName
	 * @param messages
	 */
	void pushList(String queueName, List<String> messages);
	
	/**
	 * 批量往消息队列中放入消息.
	 *
	 * @param queueName
	 * @param messages
	 */
	void pushAll(String queueName, String... messages);
	
	/**
	 * 从队列获取消息.
	 *
	 * @param queueName
	 * @return
	 */
	String pop(String queueName);
	
	/**
	 * 阻塞版pop.
	 * @see <{@link #pop(String)}
	 *
	 * @param queueName
	 * @param timeout
	 * @param unit
	 * @return
	 */
	String bpop(String queueName, long timeout, TimeUnit unit);
	
	/**
	 * 返回列表区间.
	 *
	 * @param queueName
	 * @param start
	 * @param end
	 * @return
	 */
	List<String> range(String queueName, long start, long end);
	
	/**
	 * 返回列表所有数据.
	 *
	 * @param queueName
	 * @return
	 */
	List<String> all(String queueName);
}
