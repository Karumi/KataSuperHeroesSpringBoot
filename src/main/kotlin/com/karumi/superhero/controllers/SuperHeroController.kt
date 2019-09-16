package com.karumi.superhero.controllers

import com.karumi.superhero.controllers.exceptions.NotFound
import com.karumi.superhero.data.SuperHeroStorage
import com.karumi.superhero.domain.model.SuperHero
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SuperHeroController(
  val superHeroStorage: SuperHeroStorage
) {

  @RequestMapping("/superhero")
  fun getSuperHeroesEndpoint(
    @RequestParam(name = "name", required = false) name: String?
  ): List<SuperHero> {
    val superheroes = superHeroStorage.getAll()
    return if (name != null) {
      superheroes.filter { it.name.contains(name, ignoreCase = true) }
    } else {
      superheroes
    }
  }

  @RequestMapping("/superhero/{id}")
  fun getSuperHeroByIdEndpoint(@PathVariable("id") superHeroId: String): SuperHero =
    superHeroStorage.getSuperHero(superHeroId) ?: throw NotFound

  @PostMapping("/superhero")
  fun postSuperHeroEndpoint(@RequestBody newSuperHero: SuperHero) =
    ResponseEntity(superHeroStorage.addSuperHero(newSuperHero), HttpStatus.CREATED)
}