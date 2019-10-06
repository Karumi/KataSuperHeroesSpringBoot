package com.karumi.superhero.domain.usecase

import com.karumi.superhero.data.SuperHeroRepository
import com.karumi.superhero.domain.model.SuperHero
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class GetSuperHeroById(
  val superHeroesRepository: SuperHeroRepository
) {
  operator fun invoke(id: String): Mono<SuperHero> =
    superHeroesRepository[id]
}