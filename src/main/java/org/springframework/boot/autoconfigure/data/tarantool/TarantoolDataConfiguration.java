package org.springframework.boot.autoconfigure.data.tarantool;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.tarantool.core.DefaultTarantoolExceptionTranslator;
import org.springframework.data.tarantool.core.TarantoolExceptionTranslator;

/**
 * Base configuration class for Spring Data's Tarantool support.
 * <p>
 * Registers {@link TarantoolExceptionTranslator} bean if no other bean of the same type is configured.
 *
 * @author Tatiana Blinova
 */
@Configuration(proxyBeanMethods = false)
public class TarantoolDataConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TarantoolExceptionTranslator tarantoolExceptionTranslator() {
        return new DefaultTarantoolExceptionTranslator();
    }
}
