package pl.coderslab.warsztaty_7.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.coderslab.warsztaty_7.repository.UserRepository;
import pl.coderslab.warsztaty_7.service.UserServiceImpl;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserServiceImpl userDetailsServiceImpl;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests().antMatchers("/add-admin", "/home", "/home/**", "/exception").permitAll()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/**").authenticated()
//                .and().formLogin()
//                .and().logout();
        http
                .csrf().disable()
                .authorizeRequests().antMatchers("/add-admin","/home/**","/home","/start","/start/**","/").permitAll()
                .antMatchers("/**").authenticated()
                .and().formLogin()
                .loginPage("/start/login").permitAll()
                .defaultSuccessUrl("/home", false)
                .failureUrl("/start/login?error")
                .and().logout()
                .logoutUrl("/start/logout")
                .permitAll()
                .logoutSuccessUrl("/start/login?logout");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(passwordEncoder());
    }

    @Autowired
    public void setUserDetailsService(UserServiceImpl userDetailsService) {
        this.userDetailsServiceImpl = userDetailsService;
    }
}
