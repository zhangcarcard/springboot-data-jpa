package cn.tpson.kulu.dispatcher.mq.impl;

import cn.tpson.kulu.dispatcher.mq.MessageQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisMessageQueueService implements MessageQueueService {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Override
	public void push(String queueName, String message) {
		redisTemplate.opsForList().rightPush(queueName, message);
	}

	@Override
	public void pushList(String queueName, List<String> messages) {
		redisTemplate.opsForList().rightPushAll(queueName, messages);
	}

	@Override
	public void pushAll(String queueName, String... messages) {
		redisTemplate.opsForList().rightPushAll(queueName, messages);
	}

	@Override
	public String pop(String queueName) {
		return redisTemplate.opsForList().leftPop(queueName);
	}
	
	@Override
	public String bpop(String queueName, long timeout, TimeUnit unit) {
		return redisTemplate.opsForList().leftPop(queueName, timeout, unit);
	}

	@Override
	public List<String> range(String queueName, long start, long end) {
		return redisTemplate.opsForList().range(queueName, start, end);
	}

	@Override
	public List<String> all(String queueName) {
		return redisTemplate.opsForList().range(queueName, 0, -1);
	}
}
