package org.springframework.boot.autoconfigure.data.tarantool;

import io.tarantool.driver.api.TarantoolClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.boot.autoconfigure.data.RepositoryType;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.tarantool.core.TarantoolTemplate;
import org.springframework.data.tarantool.repository.TarantoolRepository;
import org.springframework.data.tarantool.repository.config.EnableTarantoolRepositories;
import org.springframework.data.tarantool.repository.config.TarantoolRepositoryConfigurationExtension;
import org.springframework.data.tarantool.repository.support.TarantoolRepositoryFactoryBean;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Data's Tarantool Repositories.
 * <p>
 * Activates when there is no bean of type {@link TarantoolRepositoryFactoryBean}
 * configured in the context, the Spring Data Tarantool {@link TarantoolRepository} type
 * is on the classpath, the Tarantool driver API is on the classpath,
 * and there is no other configured {@link TarantoolRepository}.
 * <p>
 * Once in effect, the auto-configuration is the equivalent of enabling Tarantool repositories
 * using the {@link EnableTarantoolRepositories @EnableTarantoolRepositories}
 * annotation.
 *
 * @author Tatiana Blinova
 * @see EnableTarantoolRepositories
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({TarantoolClient.class, TarantoolRepository.class})
@ConditionalOnBean(TarantoolTemplate.class)
@ConditionalOnMissingBean({TarantoolRepositoryFactoryBean.class, TarantoolRepositoryConfigurationExtension.class})
@ConditionalOnRepositoryType(store = "tarantool", type = RepositoryType.IMPERATIVE)
@Import(TarantoolRepositoriesRegistrar.class)
@AutoConfigureAfter(TarantoolDataAutoConfiguration.class)
public class TarantoolRepositoriesAutoConfiguration {
}
