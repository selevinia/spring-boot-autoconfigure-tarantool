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
@ConfigurationProperties(prefix = "selevinia.cache.tarantool")
public class TarantoolCacheProperties {

    /**
     * Enable Tarantool cache
     */
    private boolean enabled = false;

    /**
     * Comma-separated list of cache names to create if supported by the underlying cache
     * manager. Usually, this disables the ability to create additional caches on-the-fly.
     */
    private List<String> cacheNames = new ArrayList<>();

    /**
     * Cache name prefix.
     */
    private String cacheNamePrefix;

    /**
     * Allow caching null values.
     */
    private boolean cacheNullValues = true;

    /**
     * Entry expiration. By default, the entries never expire.
     * If a duration suffix is not specified, milliseconds will be used.
     */
    private Duration timeToLive;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getCacheNames() {
        return cacheNames;
    }

    public void setCacheNames(List<String> cacheNames) {
        this.cacheNames = cacheNames;
    }

    public String getCacheNamePrefix() {
        return cacheNamePrefix;
    }

    public void setCacheNamePrefix(String cacheNamePrefix) {
        this.cacheNamePrefix = cacheNamePrefix;
    }

    public boolean isCacheNullValues() {
        return cacheNullValues;
    }

    public void setCacheNullValues(boolean cacheNullValues) {
        this.cacheNullValues = cacheNullValues;
    }

    public Duration getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(Duration timeToLive) {
        this.timeToLive = timeToLive;
    }
}
