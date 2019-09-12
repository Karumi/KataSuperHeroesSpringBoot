package com.karumi.superhero.controllers

import com.karumi.superhero.domain.model.SuperHero
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SuperHeroController {

  @RequestMapping("/superhero")
  fun getSuperHeroesEndpoint(): List<SuperHero> =
    listOf(SuperHero(id = "1", name = "Wolverine"))


  @RequestMapping("/superhero/{id}")
  fun getSuperHeroByIdEndpoint(@PathVariable("id") superHeroId: String): SuperHero =
    SuperHero(id = superHeroId, name = "Wolverine")

}