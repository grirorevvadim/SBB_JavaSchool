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
        //auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/styles/**").permitAll()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.GET, "/user").permitAll()
                .antMatchers(HttpMethod.GET, "/trains/search").permitAll()
                .antMatchers(HttpMethod.GET, "/trains").permitAll()
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .antMatchers(HttpMethod.GET, "/home").permitAll()
                .antMatchers(HttpMethod.GET, "/roots/editor", "/roots/signup", "/roots", "/roots/cform", "/roots/gform", "/roots/delete/**", "/roots/edit/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/roots/remove/**", "/roots/update/**", "/roots/create", "/roots/stations", "/roots").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/trains/all", "/trains/editor", "/trains/gform", "/trains/cform", "/trains/delete/**", "/trains/update/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/trains", "/trains/update/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/trains").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/schedules/editor", "/schedules/gform", "/schedules/delete/**", "/schedules/edit/**", "/schedules/cform", "/schedules").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/schedules/create", "/schedules/update/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/stations", "/stations/gform", "/stations/cform", "/stations/edit/**", "/stations/delete/**", "/stations/editor").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/stations", "/stations/station", "/stations/update/**", "/stations/addstation").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/tickets/bytrain").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/tickets", "/tickets/register", "/tickets/info").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/tickets").hasRole("ADMIN")
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



