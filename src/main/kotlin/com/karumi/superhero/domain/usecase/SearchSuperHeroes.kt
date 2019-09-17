package com.karumi.superhero.domain.usecase

import com.karumi.superhero.data.SuperHeroRepository
import org.springframework.stereotype.Service

@Service
class SearchSuperHeroes(
  val superHeroesRepository: SuperHeroRepository
) {
  operator fun invoke(name: String) = superHeroesRepository
    .getAll()
    .filter {
      it.name.contains(name, ignoreCase = true)
    }
}