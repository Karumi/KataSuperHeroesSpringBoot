package com.karumi.superhero.domain.usecase

import arrow.core.Either
import com.karumi.superhero.data.SuperHeroRepository
import org.springframework.stereotype.Service

@Service
class DeleteSuperHeroById(
  val superHeroesRepository: SuperHeroRepository
) {
  operator fun invoke(id: String): Either<Exception, String> =
    superHeroesRepository.delete(id)
}