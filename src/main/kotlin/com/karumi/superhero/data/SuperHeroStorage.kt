package com.karumi.superhero.data

import com.karumi.superhero.domain.model.SuperHero
import org.springframework.stereotype.Component

@Component
class SuperHeroStorage(
  private val superheroes: MutableMap<String, SuperHero> =
    mutableMapOf("1" to SuperHero(id = "1", name = "Wolverine"))
) {
  fun getSuperHero(id: String): SuperHero? = superheroes[id]
  fun addSuperHero(superHero: SuperHero): SuperHero {
    superheroes[superHero.id] = superHero
    return superHero
  }

  fun getAll(): List<SuperHero> = superheroes.values.toList()
}