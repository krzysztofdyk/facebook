package facebook.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.anonymous().and()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/api/authentication/login").permitAll()
                .mvcMatchers(HttpMethod.POST, "/api/accounts").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic();
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers(HttpMethod.POST,"/api/accounts");
        webSecurity.ignoring().antMatchers(HttpMethod.POST,"/api/authentication/login");
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService(){
        UserDetails jan = User.builder().username("jan")
                .password("password1234")
                .roles("student") // ROLE_STUDENT
                .build();

        return new InMemoryUserDetailsManager(jan);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }




}
