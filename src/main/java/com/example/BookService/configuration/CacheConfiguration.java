package com.example.BookService.configuration;

import com.example.BookService.configuration.properties.AppCacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
@EnableConfigurationProperties(AppCacheProperties.class)
public class CacheConfiguration
{
    @Bean
    public CacheManager redisCacheManager(AppCacheProperties appCacheProperties, LettuceConnectionFactory lettuceConnectionFactory) {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        Map<String, RedisCacheConfiguration> redisCacheConfiguration = new HashMap<>();
        appCacheProperties.getConfig().forEach((cacheName, cacheProperties) -> {
            redisCacheConfiguration.put(cacheName, RedisCacheConfiguration.defaultCacheConfig().entryTtl(
                    cacheProperties.getExpiry()
            ));
        });
        return RedisCacheManager.builder(lettuceConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .withInitialCacheConfigurations(redisCacheConfiguration)
                .build();
    }
}
