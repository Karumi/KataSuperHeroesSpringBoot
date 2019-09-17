package com.karumi.superhero.domain.model

data class SuperHero(
  val id: String,
  val name: String,
  val photo: String? = null
)

data class NewSuperHero(
  val name: String,
  val photo: String? = null
)