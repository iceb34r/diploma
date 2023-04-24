package com.grisha.security.сonfigs;
import com.grisha.security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authProvider;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }
    @Override
    protected  void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/registration").permitAll()
                .antMatchers("/authenticated/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/mainpage").permitAll()
                .antMatchers("/vacancycreation", "/vaclist").hasRole("EMPLOYER")
                .antMatchers("/myresume").hasRole("APPLICANT")
                .antMatchers("/**").permitAll()
                .and()
                    .formLogin()
                    .loginProcessingUrl("/login")
                    .loginPage("/login").defaultSuccessUrl("/mainpage")
                .and()
                    .csrf().disable()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login");
    }
}






//     @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests();
//    }