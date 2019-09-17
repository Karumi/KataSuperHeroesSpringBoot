package com.karumi.superhero.data

import com.karumi.superhero.data.model.mapToSuperHero
import com.karumi.superhero.domain.model.NewSuperHero
import com.karumi.superhero.domain.model.SuperHero
import org.jetbrains.annotations.TestOnly
import org.springframework.stereotype.Repository

@Repository
class SuperHeroRepository(
  private var superheroes: MutableMap<String, SuperHero> =
    mutableMapOf("1" to SuperHero(id = "1", name = "Wolverine")),
  private val incrementalId: Int = 1
) {
  operator fun get(id: String): SuperHero? = superheroes[id]
  fun addSuperHero(newSuperHero: NewSuperHero): SuperHero {
    val newId = incrementalId + 1
    val superHero = newSuperHero.mapToSuperHero("$newId")

    superheroes[superHero.id] = superHero
    return superHero
  }

  fun getAll(): List<SuperHero> = superheroes.values.toList()

  @TestOnly
  fun resetToDefault() {
    superheroes = mutableMapOf("1" to SuperHero(id = "1", name = "Wolverine"))
  }
}