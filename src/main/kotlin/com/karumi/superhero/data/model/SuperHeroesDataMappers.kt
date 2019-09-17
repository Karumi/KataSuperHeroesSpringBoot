package com.karumi.superhero.data.model

import com.karumi.superhero.domain.model.NewSuperHero
import com.karumi.superhero.domain.model.SuperHero

fun NewSuperHero.mapToSuperHero(newId: String) =
  SuperHero(
    id = newId,
    name = this.name,
    photo = this.photo
  )