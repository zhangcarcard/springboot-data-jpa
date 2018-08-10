package cn.tpson.kulu.dispatcher.mq;

/**
 * 消息队列监听器.
 */
public interface MessageQueueListener {
	/**
	 * 监听队列的名称.
	 *
	 * @return
	 */
	String getQueueName();
	
	/**
	 * 消息回调.
	 *
	 * @param message
	 */
	void onMessage(String message);
}
