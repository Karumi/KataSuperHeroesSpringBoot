package com.karumi.superhero.domain.usecase

import com.karumi.superhero.data.SuperHeroRepository
import com.karumi.superhero.domain.model.SuperHero
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class GetAllSuperHeroes(
  val superHeroesRepository: SuperHeroRepository
) {
  operator fun invoke(): Flux<SuperHero> = superHeroesRepository.getAll()
}