package com.example.shoppingapp_3.config;
import com.example.shoppingapp_3.handler.CustomizedAccessDeniedHandler;
import com.example.shoppingapp_3.handler.CustomizedAuthenticationEntryPoint;
import com.example.shoppingapp_3.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
// if using annotation method for endpoint protection, uncomment @PreAuthorize in ContentController and @EnableGlobalMethodSecurity here
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtFilter jwtFilter;
    private final CustomizedAuthenticationEntryPoint customizedAuthenticationEntryPoint;
    private final CustomizedAccessDeniedHandler customizedAccessDeniedHandler;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/user/*", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                .anyRequest().authenticated();


//        http
//                .csrf().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(customizedAuthenticationEntryPoint)
//                .accessDeniedHandler(customizedAccessDeniedHandler);

    }
}
