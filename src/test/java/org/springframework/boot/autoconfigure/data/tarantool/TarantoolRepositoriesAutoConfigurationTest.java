package org.springframework.boot.autoconfigure.data.tarantool;

import io.tarantool.driver.api.TarantoolClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.data.tarantool.emtpy.EmptyUser;
import org.springframework.boot.autoconfigure.data.tarantool.support.TestAutoConfigurationPackage;
import org.springframework.boot.autoconfigure.data.tarantool.user.User;
import org.springframework.boot.autoconfigure.data.tarantool.user.UserRepository;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ManagedTypes;
import org.springframework.data.tarantool.core.mapping.TarantoolMappingContext;
import org.springframework.data.tarantool.repository.TarantoolRepository;
import org.springframework.data.tarantool.repository.config.EnableTarantoolRepositories;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class TarantoolRepositoriesAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(TarantoolAutoConfiguration.class, TarantoolConversionAutoConfiguration.class,
                    TarantoolDataAutoConfiguration.class, TarantoolRepositoriesAutoConfiguration.class));

    @Test
    void shouldUseDefaultRepositoryConfiguration() {
        contextRunner.withUserConfiguration(TestConfiguration.class).run((context) -> {
            assertThat(context).hasSingleBean(TarantoolClient.class);
            assertThat(context).hasSingleBean(UserRepository.class);

            TarantoolMappingContext mappingContext = context.getBean(TarantoolMappingContext.class);
            ManagedTypes managedTypes = (ManagedTypes) ReflectionTestUtils.getField(mappingContext, "managedTypes");
            assertThat(managedTypes.toList()).hasSize(1);
        });
    }

    @Test
    void shouldNotUseDefaultImperativeRepositoryConfiguration() {
        contextRunner.withUserConfiguration(TestConfiguration.class)
                .withPropertyValues("spring.data.tarantool.repositories.type=reactive")
                .run((context) -> {
                    assertThat(context).hasSingleBean(TarantoolClient.class);
                    assertThat(context).doesNotHaveBean(UserRepository.class);
                });
    }

    @Test
    void shouldNotUseDefaultRepositoryConfiguration() {
        contextRunner.withUserConfiguration(TestConfiguration.class)
                .withPropertyValues("spring.data.tarantool.repositories.type=none")
                .run((context) -> {
                    assertThat(context).hasSingleBean(TarantoolClient.class);
                    assertThat(context).doesNotHaveBean(UserRepository.class);
                });
    }

    @Test
    void shouldNotUseDefaultRepositoryConfigurationWithoutDeps() {
        ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(TarantoolRepositoriesAutoConfiguration.class));

        contextRunner.withUserConfiguration(TestConfiguration.class)
                .run((context) -> {
                    assertThat(context).doesNotHaveBean(TarantoolClient.class);
                    assertThat(context).doesNotHaveBean(UserRepository.class);
                });
    }

    @Test
    void shouldUseEmptyConfiguration() {
        contextRunner.withUserConfiguration(EmptyConfiguration.class)
                .run((context) -> {
                    assertThat(context).hasSingleBean(TarantoolClient.class);
                    assertThat(context).doesNotHaveBean(TarantoolRepository.class);
                });
    }

    @Test
    void shouldUseCustomizedConfiguration() {
        contextRunner.withUserConfiguration(CustomizedConfiguration.class)
                .run((context) -> {
                    assertThat(context).hasSingleBean(TarantoolClient.class);
                    assertThat(context).hasSingleBean(UserRepository.class);
                });
    }

    @Test
    void shouldUseInvalidCustomConfiguration() {
        contextRunner.withUserConfiguration(InvalidCustomConfiguration.class)
                .run((context) -> {
                    assertThat(context).hasSingleBean(TarantoolClient.class);
                    assertThat(context).doesNotHaveBean(UserRepository.class);
                });
    }

    @Configuration(proxyBeanMethods = false)
    @TestAutoConfigurationPackage(User.class)
    static class TestConfiguration {
    }

    @Configuration(proxyBeanMethods = false)
    @TestAutoConfigurationPackage(EmptyUser.class)
    static class EmptyConfiguration {
    }

    @Configuration(proxyBeanMethods = false)
    @EnableTarantoolRepositories(basePackageClasses = UserRepository.class)
    static class CustomizedConfiguration {
    }

    @Configuration(proxyBeanMethods = false)
    @EnableTarantoolRepositories("foo.bar")
    static class InvalidCustomConfiguration {
    }
}