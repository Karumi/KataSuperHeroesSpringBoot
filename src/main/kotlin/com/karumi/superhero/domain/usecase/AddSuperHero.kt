package com.karumi.superhero.domain.usecase

import arrow.core.Either
import com.karumi.superhero.data.SuperHeroRepository
import com.karumi.superhero.domain.model.NewSuperHero
import com.karumi.superhero.domain.model.SuperHero
import org.springframework.stereotype.Service

@Service
class AddSuperHero(
  val superHeroesRepository: SuperHeroRepository
) {
  operator fun invoke(newSuperHero: NewSuperHero): Either<Exception, SuperHero> =
    superHeroesRepository
      .addSuperHero(newSuperHero)
}