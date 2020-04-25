package ru.gsmirnov.sweater.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * Configures security.
     *
     * @param http object to configure.
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()                           // sets authorization on
                .antMatchers("/").permitAll()   // full access to "/"
                .anyRequest().authenticated()              // for other requests authorization needed
                .and()
            .formLogin()                                   // sets form for login on
                .loginPage("/login")                       // sets the login page path
                .permitAll()                               // sets access for all
                .and()
            .logout()                                      // sets logout on
                .permitAll();                              // sets access for all
    }

    /**
     * Nothing encodes, nothing stores, creates user after each application start.
     * Used only for test and development.
     *
     * @return default user.
     * @throws Exception
     */
    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
