package org.springframework.boot.autoconfigure.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Tarantool-specific cache properties.
 *
 * @author Tatiana Blinova
 */
@ConfigurationProperties(prefix = "selevinia.cache")
public class TarantoolCacheProperties {

    /**
     * Comma-separated list of cache names to create if supported by the underlying cache
     * manager. Usually, this disables the ability to create additional caches on-the-fly.
     */
    private List<String> cacheNames = new ArrayList<>();

    private final Tarantool tarantool = new Tarantool();

    public List<String> getCacheNames() {
        return cacheNames;
    }

    public void setCacheNames(List<String> cacheNames) {
        this.cacheNames = cacheNames;
    }

    public Tarantool getTarantool() {
        return tarantool;
    }

    public static class Tarantool {

        /**
         * Entry expiration. By default, the entries never expire.
         * If a duration suffix is not specified, milliseconds will be used.
         */
        private Duration timeToLive;

        /**
         * Allow caching null values.
         */
        private boolean cacheNullValues = true;

        /**
         * Cache name prefix.
         */
        private String cacheNamePrefix;

        public Duration getTimeToLive() {
            return timeToLive;
        }

        public void setTimeToLive(Duration timeToLive) {
            this.timeToLive = timeToLive;
        }

        public boolean isCacheNullValues() {
            return cacheNullValues;
        }

        public void setCacheNullValues(boolean cacheNullValues) {
            this.cacheNullValues = cacheNullValues;
        }

        public String getCacheNamePrefix() {
            return cacheNamePrefix;
        }

        public void setCacheNamePrefix(String cacheNamePrefix) {
            this.cacheNamePrefix = cacheNamePrefix;
        }
    }
}
