package com.karumi.superhero.domain.usecase

import java.time.Duration
import java.time.Instant
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class GetSuperHeroNotifications {
  val heroes = arrayOf(
    "Wolverine",
    "Iroman",
    "Thor")

  operator fun invoke(): Flux<SuperHeroAlert> =
    Flux.interval(Duration.ofSeconds(1))
      .onBackpressureDrop()
      .map {
        SuperHeroAlert(
          name = heroes.random(),
          time = Instant.now().toEpochMilli())
      }
}

class SuperHeroAlert(
  val name: String,
  val time: Long
)