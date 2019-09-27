package com.karumi.superhero

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
class SuperheroApplication

fun main(args: Array<String>) {
  runApplication<SuperheroApplication>(*args)
}