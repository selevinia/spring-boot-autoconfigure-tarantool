package org.springframework.boot.autoconfigure.cache;

import io.tarantool.driver.api.TarantoolClient;
import io.tarantool.driver.api.TarantoolResult;
import io.tarantool.driver.api.tuple.TarantoolTuple;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.tarantool.TarantoolAutoConfiguration;
import org.springframework.boot.autoconfigure.data.tarantool.TarantoolConversionAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.tarantool.cache.TarantoolCacheConfiguration;
import org.springframework.data.tarantool.cache.TarantoolCacheManager;
import org.springframework.data.tarantool.core.convert.TarantoolConverter;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for cache based on Tarantool Database.
 *
 * @author Tatiana Blinova
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(TarantoolCacheProperties.class)
@ConditionalOnProperty(prefix = "selevinia.cache.tarantool", name = "enabled", havingValue = "true")
@ConditionalOnClass({TarantoolClient.class, TarantoolConverter.class})
@ConditionalOnBean({TarantoolClient.class, TarantoolConverter.class})
@AutoConfigureAfter({TarantoolAutoConfiguration.class, TarantoolConversionAutoConfiguration.class})
public class TarantoolCacheAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CacheManagerCustomizers cacheManagerCustomizers(ObjectProvider<CacheManagerCustomizer<?>> customizers) {
        return new CacheManagerCustomizers(customizers.orderedStream().collect(Collectors.toList()));
    }

    @Bean
    @ConditionalOnMissingBean(CacheManager.class)
    public TarantoolCacheManager cacheManager(TarantoolCacheProperties cacheProperties,
                                              CacheManagerCustomizers cacheManagerCustomizers,
                                              ObjectProvider<TarantoolCacheConfiguration> tarantoolCacheConfiguration,
                                              ObjectProvider<TarantoolCacheManagerBuilderCustomizer> tarantoolCacheManagerBuilderCustomizers,
                                              TarantoolClient<TarantoolTuple, TarantoolResult<TarantoolTuple>> tarantoolClient,
                                              TarantoolConverter tarantoolConverter) {
        TarantoolCacheManager.TarantoolCacheManagerBuilder builder = TarantoolCacheManager.builder(tarantoolClient, tarantoolConverter)
                .cacheDefaults(determineConfiguration(cacheProperties, tarantoolCacheConfiguration));
        List<String> cacheNames = cacheProperties.getCacheNames();
        if (!cacheNames.isEmpty()) {
            builder.initialCacheNames(new LinkedHashSet<>(cacheNames));
        }
        tarantoolCacheManagerBuilderCustomizers.orderedStream().forEach((customizer) -> customizer.customize(builder));
        return cacheManagerCustomizers.customize(builder.build());
    }

    private TarantoolCacheConfiguration determineConfiguration(TarantoolCacheProperties cacheProperties,
                                                               ObjectProvider<TarantoolCacheConfiguration> tarantoolCacheConfiguration) {
        return tarantoolCacheConfiguration.getIfAvailable(() -> createConfiguration(cacheProperties));
    }

    private TarantoolCacheConfiguration createConfiguration(TarantoolCacheProperties cacheProperties) {
        TarantoolCacheConfiguration config = TarantoolCacheConfiguration.defaultCacheConfig();
        if (cacheProperties.getTimeToLive() != null) {
            config = config.entryTtl(cacheProperties.getTimeToLive());
        }
        if (!cacheProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (cacheProperties.getCacheNamePrefix() != null) {
            config = config.prefixCacheNameWith(cacheProperties.getCacheNamePrefix());
        }
        return config;
    }
}
