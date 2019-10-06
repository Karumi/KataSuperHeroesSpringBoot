package com.karumi.superhero.data

import com.karumi.superhero.data.model.mapToDomain
import com.karumi.superhero.data.model.mapToSuperHeroEntity
import com.karumi.superhero.domain.exceptions.NotFound
import com.karumi.superhero.domain.model.NewSuperHero
import com.karumi.superhero.domain.model.SuperHero
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class SuperHeroRepository(
  private val superHeroStorage: SuperHeroDataSource
) {
  operator fun get(id: String): Mono<SuperHero> =
    Mono.just(superHeroStorage
      .findById(id.toLong()))
      .map {
        it.orElseGet { throw NotFound }.mapToDomain()
      }

  fun addSuperHero(newSuperHero: NewSuperHero): Mono<SuperHero> {

    val superHeroEntity = newSuperHero.mapToSuperHeroEntity()
    return Mono.just(superHeroStorage.save(superHeroEntity))
      .map { it.mapToDomain() }
  }

  fun getAll(): Flux<SuperHero> =
    Flux.fromIterable(superHeroStorage.findAll())
      .map { it.mapToDomain() }

  fun searchBy(name: String): Flux<SuperHero> =
    Flux.fromIterable(superHeroStorage.findByNameContainingIgnoreCase(name))
      .map { it.mapToDomain() }

  fun delete(id: String): Mono<Unit> =
    Mono.just(superHeroStorage.deleteById(id.toLong()))
}