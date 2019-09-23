package com.karumi.superhero.data

import arrow.core.Either
import arrow.core.Option
import arrow.core.toOption
import com.karumi.superhero.data.common.TryLogger
import com.karumi.superhero.data.model.mapToDomain
import com.karumi.superhero.data.model.mapToSuperHeroEntity
import com.karumi.superhero.domain.exceptions.DbStorageError
import com.karumi.superhero.domain.model.NewSuperHero
import com.karumi.superhero.domain.model.SuperHero
import org.springframework.stereotype.Repository

@Repository
class SuperHeroRepository(
  private val superHeroStorage: SuperHeroDataSource
) {
  operator fun get(id: String): Either<Exception, Option<SuperHero>> = TryLogger {
    superHeroStorage
      .findById(id.toLong()).orElse(null).toOption().map { it.mapToDomain() }
  }.toEither {
    DbStorageError
  }

  fun addSuperHero(newSuperHero: NewSuperHero): Either<Exception, SuperHero> = TryLogger {

    val superHeroEntity = newSuperHero.mapToSuperHeroEntity()

    superHeroStorage.save(superHeroEntity).mapToDomain()
  }.toEither {
    DbStorageError
  }

  fun getAll(): Either<Exception, List<SuperHero>> = TryLogger {
    superHeroStorage.findAll().map { it.mapToDomain() }
  }.toEither {
    DbStorageError
  }

  fun searchBy(name: String): Either<Exception, List<SuperHero>> = TryLogger {
    superHeroStorage.findByNameContainingIgnoreCase(name).map { it.mapToDomain() }
  }.toEither {
    DbStorageError
  }
}