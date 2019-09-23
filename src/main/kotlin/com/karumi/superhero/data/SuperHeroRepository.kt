package com.karumi.superhero.data

import com.karumi.superhero.data.model.mapToDomain
import com.karumi.superhero.data.model.mapToSuperHeroEntity
import com.karumi.superhero.domain.exceptions.NotFound
import com.karumi.superhero.domain.model.NewSuperHero
import com.karumi.superhero.domain.model.SuperHero
import org.springframework.stereotype.Repository

@Repository
class SuperHeroRepository(
  private val superHeroStorage: SuperHeroDataSource
) {
  operator fun get(id: String): SuperHero? =
    superHeroStorage
      .findById(id.toLong()).orElseThrow { NotFound }.mapToDomain()

  fun addSuperHero(newSuperHero: NewSuperHero): SuperHero {

    val superHeroEntity = newSuperHero.mapToSuperHeroEntity()

    return superHeroStorage.save(superHeroEntity).mapToDomain()
  }

  fun getAll(): List<SuperHero> = superHeroStorage.findAll().map { it.mapToDomain() }

  fun searchBy(name: String): List<SuperHero> =
    superHeroStorage.findByNameContainingIgnoreCase(name).map { it.mapToDomain() }
}