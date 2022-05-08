package per.magnus.dust.components.repos.redis;

import io.lettuce.core.ReadFrom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@PropertySource("classpath:properties/datasource.properties")
public class DustRedisConfiguration {
    @Value("${redis.host}")
    public String hostname;
    @Value("${redis.port}")
    public int port;
    @Value("${redis.timeout}")
    public int timeout;

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(hostname);
        redisStandaloneConfiguration.setPort(port);
        LettuceClientConfiguration build = LettuceClientConfiguration.builder().readFrom(ReadFrom.MASTER_PREFERRED).commandTimeout(Duration.ofMillis(timeout
        )).build();
        return new LettuceConnectionFactory(redisStandaloneConfiguration, build);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(lettuceConnectionFactory());
        return redisTemplate;
    }
}
