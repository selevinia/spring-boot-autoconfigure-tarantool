package org.springframework.boot.autoconfigure.data.tarantool;

import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.tarantool.core.DefaultTarantoolExceptionTranslator;
import org.springframework.data.tarantool.core.TarantoolExceptionTranslator;
import org.springframework.data.tarantool.core.convert.MappingTarantoolConverter;
import org.springframework.data.tarantool.core.convert.TarantoolConverter;
import org.springframework.data.tarantool.core.convert.TarantoolCustomConversions;
import org.springframework.data.tarantool.core.mapping.PrimaryKeyClass;
import org.springframework.data.tarantool.core.mapping.Space;
import org.springframework.data.tarantool.core.mapping.TarantoolMappingContext;
import org.springframework.data.tarantool.core.mapping.TarantoolSimpleTypeHolder;

import java.util.Collections;

/**
 * Base configuration class for Spring Data's Tarantool support.
 * <p>
 * Registers {@link TarantoolExceptionTranslator}, {@link TarantoolConverter},
 * {@link TarantoolCustomConversions}, {@link TarantoolMappingContext} beans
 * if no other bean of the same type is configured.
 *
 * @author Tatiana Blinova
 */
@Configuration(proxyBeanMethods = false)
public class TarantoolDataConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TarantoolMappingContext tarantoolMappingContext(ApplicationContext applicationContext, TarantoolProperties properties) throws ClassNotFoundException {
        TarantoolMappingContext mappingContext = new TarantoolMappingContext();
        mappingContext.setInitialEntitySet(new EntityScanner(applicationContext).scan(Space.class, PrimaryKeyClass.class));
        mappingContext.setSimpleTypeHolder(TarantoolSimpleTypeHolder.HOLDER);

        Class<?> strategyClass = properties.getFieldNamingStrategy();
        if (strategyClass != null) {
            mappingContext.setFieldNamingStrategy((FieldNamingStrategy) BeanUtils.instantiateClass(strategyClass));
        }

        return mappingContext;
    }

    @Bean
    @ConditionalOnMissingBean
    public TarantoolCustomConversions tarantoolCustomConversions() {
        return new TarantoolCustomConversions(Collections.emptyList());
    }

    @Bean
    @ConditionalOnMissingBean
    public TarantoolConverter tarantoolConverter(TarantoolMappingContext tarantoolMappingContext, TarantoolCustomConversions tarantoolCustomConversions) {
        MappingTarantoolConverter converter = new MappingTarantoolConverter(tarantoolMappingContext);
        converter.setCustomConversions(tarantoolCustomConversions);
        converter.afterPropertiesSet();
        return converter;
    }

    @Bean
    @ConditionalOnMissingBean
    public TarantoolExceptionTranslator tarantoolExceptionTranslator() {
        return new DefaultTarantoolExceptionTranslator();
    }
}
