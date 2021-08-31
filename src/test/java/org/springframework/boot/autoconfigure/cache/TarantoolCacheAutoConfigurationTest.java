package org.springframework.boot.autoconfigure.cache;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.data.tarantool.TarantoolAutoConfiguration;
import org.springframework.boot.autoconfigure.data.tarantool.TarantoolConversionAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.cache.CacheManager;

import static org.assertj.core.api.Assertions.assertThat;

public class TarantoolCacheAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withPropertyValues("selevinia.cache.tarantool.enabled=true")
            .withConfiguration(AutoConfigurations.of(TarantoolAutoConfiguration.class, TarantoolConversionAutoConfiguration.class,
                    TarantoolCacheAutoConfiguration.class));

    @Test
    void shouldCreateCacheManager() {
        contextRunner.run((context) -> assertThat(context).hasSingleBean(CacheManager.class));
    }

    @Test
    void shouldNotCreateCacheManagerWithoutDeps() {
        ApplicationContextRunner runner = new ApplicationContextRunner()
                .withPropertyValues("selevinia.cache.tarantool.enabled=true")
                .withConfiguration(AutoConfigurations.of(TarantoolCacheAutoConfiguration.class));
        runner.run((context) -> assertThat(context).doesNotHaveBean(CacheManager.class));
    }

    @Test
    void shouldNotCreateCacheManagerWithDisabledCache() {
        contextRunner
                .withPropertyValues("selevinia.cache.tarantool.enabled=false")
                .run((context) -> assertThat(context).doesNotHaveBean(CacheManager.class));
    }
}