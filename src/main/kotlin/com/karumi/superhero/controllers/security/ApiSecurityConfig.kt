package com.karumi.superhero.controllers.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebSecurity
@EnableWebFluxSecurity
class ApiSecurityConfig {

  fun configure(auth: AuthenticationManagerBuilder) {
    auth.inMemoryAuthentication()
      .withUser("user")
      .password("password")
      .password(encoder().encode("userPass"))
      .roles("USER")
      .and()
      .withUser("admin")
      .password("password")
      .password(encoder().encode("adminPass"))
      .roles("USER", "ADMIN")
  }

  /**
   * Sample in-memory user details service.
   */
  // Removes warning from "withDefaultPasswordEncoder()"
  @Bean
  fun userDetailsService(): MapReactiveUserDetailsService {
    return MapReactiveUserDetailsService(
      User
        .withUsername("user")
        .password(encoder().encode("userPass"))
        .roles("USER")
        .build(),
      User
        .withUsername("admin")
        .password(encoder().encode("adminPass"))
        .roles("USER", "ADMIN")
        .build())
  }

  @Bean
  fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
    return http
      .csrf().disable()
      .httpBasic().and()
      .authorizeExchange()
      .pathMatchers(HttpMethod.POST, "/superhero").hasRole("ADMIN")
      .anyExchange().authenticated()
      .and()
      .build()
  }

  @Bean
  fun encoder(): PasswordEncoder =
    BCryptPasswordEncoder()
}