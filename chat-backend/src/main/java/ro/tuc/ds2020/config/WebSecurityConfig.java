package ro.tuc.ds2020.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF
                .authorizeRequests().and()
                // Configure CORS
                .cors()
                .and()
                // Define authorization requests
                .authorizeRequests()
                .antMatchers("/ws/**").permitAll() // Allow all requests to /ws
                // Define other authenticated requests
                .anyRequest().authenticated()
        // Other configurations (like formLogin, httpBasic, logout etc.)
        // ...
        ;
    }
}
