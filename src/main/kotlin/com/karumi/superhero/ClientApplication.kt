package com.karumi.superhero

import com.karumi.superhero.domain.usecase.SuperHeroAlert
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux

fun main(args: Array<String>) {
  val client = WebClient.create("http://localhost:8080/notifications")

  client.get()
    .headers { it.setBasicAuth("user", "userPass") }
    .retrieve()
    .bodyToFlux<SuperHeroAlert>()
    .subscribe {
      println(it)
    }

  while (true) {
  }
}