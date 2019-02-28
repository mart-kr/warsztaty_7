package pl.coderslab.warsztaty_7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.coderslab.warsztaty_7.repository.UserRepository;

@Configuration
@EnableAsync
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableTransactionManagement
public class ApplicationConfig {

    @Bean
    public AuditorAware<Long> auditorAware() {
        return new AuditorAwareImpl();
    }
}