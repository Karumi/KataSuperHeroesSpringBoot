package com.karumi.superhero.domain.usecase

import com.karumi.superhero.data.SuperHeroRepository
import com.karumi.superhero.domain.model.NewSuperHero
import com.karumi.superhero.domain.model.SuperHero
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AddSuperHero(
  val superHeroesRepository: SuperHeroRepository
) {
  operator fun invoke(newSuperHero: NewSuperHero): Mono<SuperHero> =
    superHeroesRepository
      .addSuperHero(newSuperHero)
}