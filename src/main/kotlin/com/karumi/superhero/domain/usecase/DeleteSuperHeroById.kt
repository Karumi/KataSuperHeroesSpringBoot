package com.karumi.superhero.domain.usecase

import com.karumi.superhero.data.SuperHeroRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class DeleteSuperHeroById(
  val superHeroesRepository: SuperHeroRepository
) {
  operator fun invoke(id: String): Mono<Unit> =
    superHeroesRepository.delete(id)
}