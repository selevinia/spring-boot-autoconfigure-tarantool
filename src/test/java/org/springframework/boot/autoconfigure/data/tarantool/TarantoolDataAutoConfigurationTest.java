package org.springframework.boot.autoconfigure.data.tarantool;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.data.tarantool.core.TarantoolExceptionTranslator;
import org.springframework.data.tarantool.core.TarantoolTemplate;
import org.springframework.data.tarantool.core.convert.TarantoolConverter;
import org.springframework.data.tarantool.core.convert.TarantoolCustomConversions;
import org.springframework.data.tarantool.core.mapping.TarantoolMappingContext;

import static org.assertj.core.api.Assertions.assertThat;

public class TarantoolDataAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(TarantoolAutoConfiguration.class, TarantoolConversionAutoConfiguration.class,
                    TarantoolDataAutoConfiguration.class));

    @Test
    void shouldCreateTemplate() {
        contextRunner.run((context) -> {
            assertThat(context).hasSingleBean(TarantoolTemplate.class);
            assertThat(context).hasSingleBean(TarantoolMappingContext.class);
            assertThat(context).hasSingleBean(TarantoolCustomConversions.class);
            assertThat(context).hasSingleBean(TarantoolConverter.class);
            assertThat(context).hasSingleBean(TarantoolExceptionTranslator.class);
        });
    }

    @Test
    void shouldNotCreateTemplateWithoutDeps() {
        ApplicationContextRunner runner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(TarantoolDataAutoConfiguration.class));
        runner.run((context) -> assertThat(context).doesNotHaveBean(TarantoolTemplate.class));
    }

}