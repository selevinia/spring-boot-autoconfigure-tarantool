package org.springframework.boot.autoconfigure.data.tarantool;

import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;
import org.springframework.data.tarantool.repository.config.EnableTarantoolRepositories;
import org.springframework.data.tarantool.repository.config.TarantoolRepositoryConfigurationExtension;

import java.lang.annotation.Annotation;

/**
 * {@link ImportBeanDefinitionRegistrar} used to auto-configure Spring Data Tarantool Repositories.
 *
 * @author Tatiana Blinova
 */
public class TarantoolRepositoriesRegistrar extends AbstractRepositoryConfigurationSourceSupport {

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableTarantoolRepositories.class;
    }

    @Override
    protected Class<?> getConfiguration() {
        return EnableTarantoolRepositoriesConfiguration.class;
    }

    @Override
    protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
        return new TarantoolRepositoryConfigurationExtension();
    }

    @EnableTarantoolRepositories
    private static class EnableTarantoolRepositoriesConfiguration {
    }
}
