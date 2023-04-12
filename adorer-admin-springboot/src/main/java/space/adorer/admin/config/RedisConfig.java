package space.adorer.admin.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 配置类
 *
 * @author adorer
 * @version 1.0
 * @since 2023/4/11 11:35:19
 */
public class RedisConfig {
    /**
     * 配置 Redis 序列化与反序列化
     */
    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        FastJsonRedisSerializer<?> serializer = new FastJsonRedisSerializer<>(Object.class);

        // 使用 StringRedisSerializer 来序列化和反序列化 redis 的 key 值
        template.setKeySerializer(new StringRedisSerializer());
        // 使用 FastJson 来序列化和反序列化 redis 的 value 值
        template.setValueSerializer(serializer);
        // Hash 的 key 也采用 StringRedisSerializer 的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        // Hash 的 value 值也采用 FastJson 来进行序列化和反序列化
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }
}
