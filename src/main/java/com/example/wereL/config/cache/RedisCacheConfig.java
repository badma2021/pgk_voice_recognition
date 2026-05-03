package com.example.wereL.config.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisCacheConfig {
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        KryoRedisSerializer<Object> kryoSerializer = new KryoRedisSerializer<>(Object.class);

        RedisSerializationContext.SerializationPair<Object> valueSerializer =
                RedisSerializationContext.SerializationPair.fromSerializer(kryoSerializer);
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeValuesWith(valueSerializer);

        Map<String, RedisCacheConfiguration> configs = new HashMap<>();
        configs.put(
                CacheNames.EXPENSE_BY_CATEGORY,
                RedisCacheConfiguration.defaultCacheConfig()
                        .serializeValuesWith(valueSerializer)
        );//infinite
        configs.put(
                CacheNames.CATEGORY_BY_TIME,
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1))
                        .serializeValuesWith(valueSerializer)
        );
        return RedisCacheManager.builder(factory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(configs)
                .build();
    }
}