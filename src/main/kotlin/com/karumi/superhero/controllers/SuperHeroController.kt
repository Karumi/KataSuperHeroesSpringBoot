package com.karumi.superhero.controllers

import com.karumi.superhero.domain.model.SuperHero
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SuperHeroController {

  @RequestMapping("/superhero")
  fun getSuperHeroesEndpoint() : List<SuperHero> {
    return listOf(SuperHero(name = "Wolverine"))
  }
}