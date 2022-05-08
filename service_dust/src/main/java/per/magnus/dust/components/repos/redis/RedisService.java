package per.magnus.dust.components.repos.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
@SuppressWarnings("unused")
public class RedisService {

    @Resource
    RedisTemplate<String, String> redisTemplate;

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, String value, long time) {
        redisTemplate.opsForValue().set(key, value, time);
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean exist(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void flushKey(String key, long ttl) {
        redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
    }
}
