package com.karumi.superhero.controllers

import com.karumi.superhero.domain.model.SuperHero
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SuperHeroController {

  @RequestMapping("/superhero")
  fun getSuperHeroesEndpoint(
    @RequestParam(name = "name", required = false) name: String?
  ): List<SuperHero> {
    val superheroes = listOf(SuperHero(id = "1", name = "Wolverine"))
    return if (name != null) {
      superheroes.filter { it.name.contains(name, ignoreCase = true) }
    } else {
      superheroes
    }
  }

  @RequestMapping("/superhero/{id}")
  fun getSuperHeroByIdEndpoint(@PathVariable("id") superHeroId: String): SuperHero =
    SuperHero(id = superHeroId, name = "Wolverine")
}