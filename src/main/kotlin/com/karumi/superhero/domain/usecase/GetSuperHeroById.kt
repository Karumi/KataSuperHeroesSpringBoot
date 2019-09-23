package com.karumi.superhero.domain.usecase

import arrow.core.Either
import arrow.core.flatMap
import com.karumi.superhero.data.SuperHeroRepository
import com.karumi.superhero.domain.exceptions.NotFound
import com.karumi.superhero.domain.model.SuperHero
import org.springframework.stereotype.Service

@Service
class GetSuperHeroById(
  val superHeroesRepository: SuperHeroRepository
) {
  operator fun invoke(id: String): Either<Exception, SuperHero> =
    superHeroesRepository[id].flatMap { it.toEither { NotFound } }
}