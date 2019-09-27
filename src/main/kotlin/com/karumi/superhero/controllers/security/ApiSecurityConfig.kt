package com.karumi.superhero.controllers.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class ApiSecurityConfig : WebSecurityConfigurerAdapter() {

  override fun configure(auth: AuthenticationManagerBuilder) {
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

  override fun configure(http: HttpSecurity) {
    http
      .httpBasic()
      .and()
      .authorizeRequests()
      .antMatchers(HttpMethod.POST, "/superhero").hasRole("ADMIN")
      .anyRequest().authenticated()
      .and()
      .csrf().disable()
      .formLogin().disable()
  }

  @Bean
  fun encoder(): PasswordEncoder =
    BCryptPasswordEncoder()
}