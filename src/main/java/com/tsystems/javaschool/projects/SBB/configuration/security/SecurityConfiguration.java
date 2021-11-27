package com.tsystems.javaschool.projects.SBB.configuration.security;

import com.tsystems.javaschool.projects.SBB.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/styles/**","/images/**").permitAll()
                .antMatchers("/users", "/user", "/trains/search", "/trains", "/", "/home","/stations/schedule","/stations/scheduleinfo").permitAll()
                .antMatchers( "/roots/**", "/trains/**", "/schedules/**", "/stations/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/tickets/bytrain").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/tickets", "/tickets/register" ).hasRole("USER")
                .antMatchers(HttpMethod.GET,"/tickets/info").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/tickets").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin()
                .and().logout().deleteCookies().logoutUrl("/logout")
                .logoutSuccessUrl("/home")
                .and().rememberMe().tokenValiditySeconds(1800).userDetailsService(userService);

    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }
}



