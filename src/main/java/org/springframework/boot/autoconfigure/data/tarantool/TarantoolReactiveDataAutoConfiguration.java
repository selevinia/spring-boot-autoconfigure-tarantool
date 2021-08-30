package org.springframework.boot.autoconfigure.data.tarantool;

import io.tarantool.driver.api.TarantoolClient;
import io.tarantool.driver.api.TarantoolResult;
import io.tarantool.driver.api.tuple.TarantoolTuple;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.tarantool.core.ReactiveTarantoolOperations;
import org.springframework.data.tarantool.core.ReactiveTarantoolTemplate;
import org.springframework.data.tarantool.core.TarantoolExceptionTranslator;
import org.springframework.data.tarantool.core.convert.TarantoolConverter;
import reactor.core.publisher.Flux;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Data's reactive Tarantool support.
 * <p>
 * Registers {@link ReactiveTarantoolTemplate} bean if no other bean of the same type is configured.
 *
 * @author Tatiana Blinova
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({TarantoolClient.class, ReactiveTarantoolTemplate.class, Flux.class})
@ConditionalOnBean(TarantoolClient.class)
@Import(TarantoolDataConfiguration.class)
@AutoConfigureAfter({TarantoolAutoConfiguration.class, TarantoolConversionAutoConfiguration.class})
public class TarantoolReactiveDataAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ReactiveTarantoolOperations.class)
    public ReactiveTarantoolTemplate reactiveTarantoolTemplate(TarantoolClient<TarantoolTuple, TarantoolResult<TarantoolTuple>> tarantoolClient,
                                                               TarantoolConverter tarantoolConverter,
                                                               TarantoolExceptionTranslator tarantoolExceptionTranslator) {
        return new ReactiveTarantoolTemplate(tarantoolClient, tarantoolConverter, tarantoolExceptionTranslator);
    }
}
