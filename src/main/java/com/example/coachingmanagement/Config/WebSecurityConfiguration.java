package com.example.coachingmanagement.Config;

import com.example.coachingmanagement.Models.User;
import com.example.coachingmanagement.Repository.UserRepository;
import com.example.coachingmanagement.Services.UserDetailsServiceImpl;
import com.example.coachingmanagement.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    UserDetailsServiceImpl userDetailsService;
    UserRepository userRepository;
    @Autowired
    public WebSecurityConfiguration(UserDetailsServiceImpl userDetailsService,UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.userRepository=userRepository;
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception{
        return authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests().antMatchers("/","/login","/logout").permitAll();
        http.authorizeRequests().antMatchers("/self","/self/**").access("hasAuthority('admin') or hasAuthority('Student') or hasAuthority('Staff') or hasAuthority('Teacher')");
        http.authorizeRequests().antMatchers("/admin","/admin/**").access("hasAuthority('admin')");
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
        http.authorizeRequests().and().formLogin().loginPage("/login").permitAll()
                .defaultSuccessUrl("/",true)
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                        String username = userDetails.getUsername();
                        User user=userRepository.findByUsername(username);
                        java.util.Date date=new java.util.Date();
                        user.setLastLoginTime(new java.sql.Time(date.getTime()));
                        user.setLastLoginDate(new java.sql.Date(date.getTime()));
                        userRepository.update(user);

                        httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
                    }
                }).failureUrl("/login?error=true").usernameParameter("username")
                .passwordParameter("password").and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }
}
