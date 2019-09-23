package com.karumi.superhero.controllers

import com.karumi.superhero.domain.model.NewSuperHero
import com.karumi.superhero.domain.model.SuperHero
import com.karumi.superhero.domain.usecase.AddSuperHero
import com.karumi.superhero.domain.usecase.GetAllSuperHeroes
import com.karumi.superhero.domain.usecase.GetSuperHeroById
import com.karumi.superhero.domain.usecase.SearchSuperHeroes
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
  val getAllSuperHeroes: GetAllSuperHeroes,
  val searchSuperHeroes: SearchSuperHeroes,
  val getSuperHeroById: GetSuperHeroById,
  val addSuperHero: AddSuperHero
) {

  @RequestMapping("/superhero")
  fun getSuperHeroesEndpoint(
    @RequestParam(name = "name", required = false) name: String?
  ): List<SuperHero> =
    if (name == null) {
      getAllSuperHeroes()
    } else {
      searchSuperHeroes(name)
    }.fold(
      ifRight = { it },
      ifLeft = { throw it }
    )

  @RequestMapping("/superhero/{id}")
  fun getSuperHeroByIdEndpoint(@PathVariable("id") superHeroId: String): SuperHero =
    getSuperHeroById(superHeroId).fold(
      ifRight = { it },
      ifLeft = { throw it }
    )

  @PostMapping("/superhero")
  fun postSuperHeroEndpoint(@RequestBody newSuperHero: NewSuperHero) =
    addSuperHero(newSuperHero).fold(
      ifRight = { ResponseEntity(it, HttpStatus.CREATED) },
      ifLeft = { throw it }
    )
}