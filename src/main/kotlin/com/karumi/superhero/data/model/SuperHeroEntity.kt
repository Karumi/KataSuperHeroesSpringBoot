package com.karumi.superhero.data.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class SuperHeroEntity(
  @Id @GeneratedValue val id: Long? = null,
  val name: String,
  @Column(nullable = true) val photo: String? = null
)