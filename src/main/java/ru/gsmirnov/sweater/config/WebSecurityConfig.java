package ru.gsmirnov.sweater.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import ru.gsmirnov.sweater.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    @Autowired
    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    /**
     * Configures security.
     *
     * @param http object to configure.
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()                                            // sets authorization on
                .antMatchers("/", "/registration").permitAll()   // full access to "/", "/registration"
                .anyRequest().authenticated()                               // for other requests authorization needed
                .and()
            .formLogin()                                                    // sets form for login on
                .loginPage("/login")                                        // sets the login page path
                .permitAll()                                                // sets access for all
                .and()
            .logout()                                                       // sets logout on
                .permitAll();                                               // sets access for all
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
