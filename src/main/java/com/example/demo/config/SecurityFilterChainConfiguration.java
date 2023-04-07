package com.example.demo.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConditionalOnProperty(prefix = "security", name = "method", havingValue = "supported")
public class SecurityFilterChainConfiguration {

  private static final String TWS_PASS = "$2a$10$5nmlMz/K93HtN6/xL999IeUx0ZQQC1IRNJ32LAdYEJGrLvlTTerYq";

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authRequests -> authRequests
            .mvcMatchers("/actuator/health").permitAll()
            .mvcMatchers("/actuator/prometheus").permitAll()
            .anyRequest().authenticated()
        )
        .httpBasic(withDefaults());
    return http.build();
  }

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    manager.createUser(User.withUsername("tws-user")
        .password(TWS_PASS)
        .roles("USER")
        .build());
    return manager;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
