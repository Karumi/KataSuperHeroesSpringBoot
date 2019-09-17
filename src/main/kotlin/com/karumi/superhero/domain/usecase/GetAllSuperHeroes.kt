package com.karumi.superhero.domain.usecase

import com.karumi.superhero.data.SuperHeroRepository
import org.springframework.stereotype.Service

@Service
class GetAllSuperHeroes(
  val superHeroesRepository: SuperHeroRepository
) {
  operator fun invoke() = superHeroesRepository.getAll()
}