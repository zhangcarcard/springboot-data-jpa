package cn.tpson.kulu.dispatcher.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public <K, V> RedisTemplate<K, V> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<K, V> template = new RedisTemplate();
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        FastJson2JsonRedisSerializer valueSerializer = new FastJson2JsonRedisSerializer(Object.class);

        template.setConnectionFactory(factory);
        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);
        template.setHashValueSerializer(valueSerializer);
        
        return template;
    }
}
