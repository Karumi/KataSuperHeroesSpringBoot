package com.karumi.superhero.domain.usecase

import com.karumi.superhero.data.SuperHeroRepository
import com.karumi.superhero.domain.model.SuperHero
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class SearchSuperHeroes(
  val superHeroesRepository: SuperHeroRepository
) {
  operator fun invoke(name: String): Flux<SuperHero> = superHeroesRepository
    .searchBy(name = name)
}