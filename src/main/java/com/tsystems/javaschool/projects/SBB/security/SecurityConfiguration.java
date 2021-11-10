package com.tsystems.javaschool.projects.SBB.security;

import com.tsystems.javaschool.projects.SBB.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        auth.authenticationProvider(daoAuthenticationProvider());
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, "/users").permitAll()
//                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/user").permitAll()
//                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/trains/search").permitAll()
//                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/trains").permitAll()
//                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/").permitAll()
//                .and().authorizeRequests().antMatchers("/", "/roots/**", "/schedules/**", "/stations/**", "/tickets/**", "/trains/**", "/users/**").hasRole("ADMIN")
//                .anyRequest().authenticated()
//                .and().formLogin()
//                .defaultSuccessUrl("/")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll()
//                .logoutSuccessUrl("/");
//    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.GET, "/user").permitAll()
                .antMatchers(HttpMethod.GET, "/trains/search").permitAll()
                .antMatchers(HttpMethod.GET, "/trains").permitAll()
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .antMatchers(HttpMethod.GET, "/home").permitAll()
                .antMatchers(HttpMethod.GET, "/roots/editor").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin()
                .and().logout().deleteCookies().logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and().rememberMe().tokenValiditySeconds(1800).userDetailsService(userService);
        //    .antMatchers("/", "/roots/**", "/schedules/**", "/stations/**", "/tickets/**", "/trains/**", "/users/**").hasRole("ADMIN");
        //.and().formLogin()
//                .defaultSuccessUrl("/")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll()
//                .logoutSuccessUrl("/");
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }
}



