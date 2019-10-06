package com.karumi.superhero.controllers

import com.karumi.superhero.domain.model.NewSuperHero
import com.karumi.superhero.domain.model.SuperHero
import com.karumi.superhero.domain.usecase.AddSuperHero
import com.karumi.superhero.domain.usecase.DeleteSuperHeroById
import com.karumi.superhero.domain.usecase.GetAllSuperHeroes
import com.karumi.superhero.domain.usecase.GetSuperHeroById
import com.karumi.superhero.domain.usecase.SearchSuperHeroes
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class SuperHeroController(
  val getAllSuperHeroes: GetAllSuperHeroes,
  val searchSuperHeroes: SearchSuperHeroes,
  val getSuperHeroById: GetSuperHeroById,
  val addSuperHero: AddSuperHero,
  val deleteSuperHeroById: DeleteSuperHeroById
) {

  @RequestMapping("/superhero")
  fun getSuperHeroesEndpoint(
    @RequestParam(name = "name", required = false) name: String?
  ): Flux<SuperHero> =
    if (name == null) {
      getAllSuperHeroes()
    } else {
      searchSuperHeroes(name)
    }

  @RequestMapping("/superhero/{id}")
  fun getSuperHeroByIdEndpoint(@PathVariable("id") superHeroId: String): Mono<SuperHero> =
    getSuperHeroById(superHeroId)

  @PostMapping("/superhero")
  @ResponseStatus(value = HttpStatus.CREATED)
  fun postSuperHeroEndpoint(@RequestBody newSuperHero: NewSuperHero) =
    addSuperHero(newSuperHero)

  @DeleteMapping("/superhero/{id}")
  @Secured("ROLE_ADMIN")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  fun deleteSuperHeroEndpoint(@PathVariable("id") superHeroId: String): Mono<Unit> =
    deleteSuperHeroById(superHeroId)
}