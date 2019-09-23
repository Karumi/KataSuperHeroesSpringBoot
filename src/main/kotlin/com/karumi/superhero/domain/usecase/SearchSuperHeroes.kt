package com.karumi.superhero.domain.usecase

import arrow.core.Either
import com.karumi.superhero.data.SuperHeroRepository
import com.karumi.superhero.domain.model.SuperHero
import org.springframework.stereotype.Service

@Service
class SearchSuperHeroes(
  val superHeroesRepository: SuperHeroRepository
) {
  operator fun invoke(name: String): Either<Exception, List<SuperHero>> = superHeroesRepository
    .searchBy(name = name)
}