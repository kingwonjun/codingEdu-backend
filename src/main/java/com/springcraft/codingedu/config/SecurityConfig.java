package com.springcraft.codingedu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user1").password("password").roles("USER", "VIEW_BALANCE").build());
        manager.createUser(users.username("user2").password("password").roles("USER").build());
        manager.createUser(users.username("admin").password("password").roles("ADMIN").build());
        return manager;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain approvalsSecurityFilterChain(HttpSecurity http) throws Exception {
        String[] approvalsPaths = { "/accounts/approvals/**", "/loans/approvals/**", "/credit-cards/approvals/**" };
        http
                .securityMatcher(approvalsPaths)
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().hasRole("ADMIN")
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain bankingSecurityFilterChain(HttpSecurity http) throws Exception {
        String[] bankingPaths = { "/accounts/**", "/loans/**", "/credit-cards/**", "/balances/**" };
        String[] viewBalancePaths = { "/balances/**" };
        http
                .securityMatcher(bankingPaths)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(viewBalancePaths).hasRole("VIEW_BALANCE")
                        .anyRequest().hasRole("USER")
                );
        return http.build();
    }

    /*@Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        String[] allowedPaths = { "/", "/user-login", "/user-logout", "/notices", "/contact", "/register", "/api/v1/auth/signup" };
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(allowedPaths).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((formLogin) -> formLogin
                        .loginPage("/user-login")
                        .loginProcessingUrl("/user-login")
                )
                .logout((logout) -> logout
                        .logoutUrl("/user-logout")
                        .logoutSuccessUrl("/?logout")
                );
        return http.build();
    }*/
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/public").permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home")
                        .failureUrl("/login?error")
                        .permitAll()
                );
        return http.build();
    }
}
