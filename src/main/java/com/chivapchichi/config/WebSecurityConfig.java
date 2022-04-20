package com.chivapchichi.config;

import com.chivapchichi.service.security.JwtConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final JwtConfigurer jwtConfigurer;

    @Autowired
    public WebSecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, JwtConfigurer jwtConfigurer) {
        this.userDetailsService = userDetailsService;
        this.jwtConfigurer = jwtConfigurer;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //.antMatchers(HttpMethod.POST, "/tournament/**").hasRole("USER")
                //.antMatchers(HttpMethod.GET, "/tournament/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/**").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                //.antMatchers("/admin-panel/**").hasRole("ADMIN")
                .antMatchers("/admin/api/**").hasRole("ADMIN")
                //.antMatchers("/registration").permitAll()
                .antMatchers("/login-api/auth/login").permitAll()
                .antMatchers("/login-api/auth/logout").hasAnyRole("ADMIN", "USER")
                //.antMatchers("/login").permitAll()
                //.antMatchers("/makelogout").hasAnyRole("ADMIN", "USER")
                .antMatchers("/registration/api").permitAll()
                //.antMatchers("/main.js").permitAll()
                //.antMatchers("/select.js").permitAll()
                //.antMatchers("/style.css").permitAll()
                //.antMatchers("/favicon.ico").permitAll()
                .antMatchers("/myinfo/**").hasAnyRole("ADMIN", "USER")
                .anyRequest().authenticated()
                .and()
                .apply(jwtConfigurer);
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
}
