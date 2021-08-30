package org.springframework.boot.autoconfigure.cache;

import org.springframework.data.tarantool.cache.TarantoolCacheManager.TarantoolCacheManagerBuilder;

/**
 * Callback interface that can be used to customize a {@link TarantoolCacheManagerBuilder}.
 *
 * @author Tatiana Blinova
 */
@FunctionalInterface
public interface TarantoolCacheManagerBuilderCustomizer {

    /**
     * Customize the {@link TarantoolCacheManagerBuilder}.
     *
     * @param builder the builder to customize
     */
    void customize(TarantoolCacheManagerBuilder builder);
}
