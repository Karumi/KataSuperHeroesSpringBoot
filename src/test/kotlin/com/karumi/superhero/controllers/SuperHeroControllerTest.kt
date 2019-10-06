package com.karumi.superhero.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.karumi.superhero.data.SuperHeroRepository
import com.karumi.superhero.domain.exceptions.NotFound
import com.karumi.superhero.domain.model.NewSuperHero
import com.karumi.superhero.domain.model.SuperHero
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SuperHeroControllerTest(
  @Autowired val mockMvc: WebTestClient
) {

  val ANY_SUPERHERO = SuperHero(id = "1", name = "Wolverine")
  val ANY_NEW_SUPERHERO = NewSuperHero(name = "Wolverine")
  val WRONG_NEW_SUPERHERO = "{}"

  @MockkBean
  lateinit var superHeroRepository: SuperHeroRepository

  @Test
  fun `should return the list of superheroes when contains superheroes`() {
    every { superHeroRepository.getAll() } returns Flux.fromIterable(listOf(ANY_SUPERHERO))

    mockMvc
      .get()
      .uri("/superhero")
      .headers(::withAnyUser)
      .exchange()

      .expectStatus().isOk
      .expectBodyList(SuperHero::class.java)
      .contains(ANY_SUPERHERO)
  }

  @Test
  fun `should return the list of superheroes filters by name`() {
    every {
      superHeroRepository.searchBy(eq("wol"))
    } returns Flux.fromIterable(listOf(ANY_SUPERHERO))

    mockMvc
      .get()
      .uri("/superhero?name=wol")
      .headers(::withAnyUser)
      .exchange()

      .expectStatus().isOk
      .expectBodyList(SuperHero::class.java)
      .contains(ANY_SUPERHERO)
  }

  @Test
  fun `should return a superhero if the id exist`() {
    every { superHeroRepository[any()] } returns Mono.just(ANY_SUPERHERO)

    mockMvc
      .get()
      .uri("/superhero/1")
      .headers(::withAnyUser)
      .exchange()

      .expectStatus().isOk
      .expectBody<SuperHero>()
      .isEqualTo(ANY_SUPERHERO)
  }

  @Test
  fun `should return a 404 if the id not exist`() {
    every { superHeroRepository[any()] } returns Mono.error(NotFound)

    mockMvc
      .get()
      .uri("/superhero/1")
      .headers(::withAnyUser)
      .exchange()

      .expectStatus()
      .isNotFound
  }

  @Test
  fun `should return the superhero created if the values are correct`() {
    every { superHeroRepository.addSuperHero(any()) } returns Mono.just(ANY_SUPERHERO)

    mockMvc
      .post()
      .uri("/superhero")
      .headers(::withAdminUser)
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .body(Mono.just(ANY_NEW_SUPERHERO), NewSuperHero::class.java)
      .exchange()

      .expectStatus().isCreated
      .expectBody<SuperHero>()
      .isEqualTo(ANY_SUPERHERO)
  }

  @Test
  fun `should return the unauthorized if the user is not admin`() {
    every { superHeroRepository.addSuperHero(any()) } returns Mono.just(ANY_SUPERHERO)

    mockMvc
      .post()
      .uri("/superhero")
      .headers(::withAnyUser)
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .body(Mono.just(ANY_NEW_SUPERHERO), NewSuperHero::class.java)
      .exchange()

      .expectStatus().isForbidden
  }

  @Test
  fun `should return an error if the new superhero is invalid`() {
    mockMvc
      .post()
      .uri("/superhero")
      .headers(::withAdminUser)
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .body(BodyInserters.fromObject(WRONG_NEW_SUPERHERO))
      .exchange()

      .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
  }

  @Test
  fun `should return sucess if the new superhero is deleted`() {
    every { superHeroRepository.delete(any()) } returns Mono.empty()

    mockMvc
      .delete()
      .uri("/superhero/1")
      .headers(::withAdminUser)
      .exchange()

      .expectStatus().isNoContent
  }
}

private fun withAnyUser(headers: HttpHeaders) =
  headers.setBasicAuth("user", "userPass")

private fun withAdminUser(headers: HttpHeaders) =
  headers.setBasicAuth("admin", "adminPass")

private fun <T> T.toJson(): String {
  val objectMapper = ObjectMapper()
  return objectMapper.writeValueAsString(this)
}