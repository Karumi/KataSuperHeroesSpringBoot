package com.karumi.superhero

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SuperheroApplication

fun main(args: Array<String>) {
  runApplication<SuperheroApplication>(*args)
}