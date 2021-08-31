package org.springframework.boot.autoconfigure.data.tarantool;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.data.tarantool.core.convert.TarantoolConverter;
import org.springframework.data.tarantool.core.convert.TarantoolCustomConversions;
import org.springframework.data.tarantool.core.mapping.TarantoolMappingContext;

import static org.assertj.core.api.Assertions.assertThat;

public class TarantoolConversionAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(TarantoolConversionAutoConfiguration.class));

    @Test
    void shouldCreateConversionBeans() {
        contextRunner.run((context) -> {
            assertThat(context).hasSingleBean(TarantoolMappingContext.class);
            assertThat(context).hasSingleBean(TarantoolCustomConversions.class);
            assertThat(context).hasSingleBean(TarantoolConverter.class);
        });
    }
}