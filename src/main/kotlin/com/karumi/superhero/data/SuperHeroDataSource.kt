package com.karumi.superhero.data

import com.karumi.superhero.data.model.SuperHeroEntity
import org.springframework.data.repository.CrudRepository

interface SuperHeroDataSource : CrudRepository<SuperHeroEntity, Long> {
  fun findByNameContainingIgnoreCase(name: String): List<SuperHeroEntity>
}