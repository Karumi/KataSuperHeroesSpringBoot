package com.karumi.superhero.controllers

import arrow.core.None
import arrow.core.right
import arrow.core.some
import com.fasterxml.jackson.databind.ObjectMapper
import com.karumi.superhero.data.SuperHeroRepository
import com.karumi.superhero.domain.model.NewSuperHero
import com.karumi.superhero.domain.model.SuperHero
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class SuperHeroControllerTest(
  @Autowired val mockMvc: MockMvc
) {

  val ANY_SUPERHERO = SuperHero(id = "1", name = "Wolverine")
  val ANY_NEW_SUPERHERO = NewSuperHero(name = "Wolverine")
  val WRONG_NEW_SUPERHERO = "{}"

  @MockkBean
  lateinit var superHeroRepository: SuperHeroRepository

  @Test
  @WithMockUser("USER")
  fun `should return the list of superheroes when contains superheroes`() {
    every { superHeroRepository.getAll() } returns listOf(ANY_SUPERHERO).right()

    mockMvc.perform(MockMvcRequestBuilders
      .get("/superhero"))

      .andExpect(status().isOk)
      .andExpect(content().json((listOf(ANY_SUPERHERO).toJson()), true))
  }

  @Test
  @WithMockUser("USER")
  fun `should return the list of superheroes filters by name`() {
    every { superHeroRepository.searchBy(eq("wol")) } returns listOf(ANY_SUPERHERO).right()

    mockMvc.perform(MockMvcRequestBuilders
      .get("/superhero?name=wol"))

      .andExpect(status().isOk)
      .andExpect(content().json((listOf(ANY_SUPERHERO).toJson()), true))
  }

  @Test
  @WithMockUser("USER")
  fun `should return a superhero if the id exist`() {
    every { superHeroRepository[any()] } returns ANY_SUPERHERO.some().right()

    mockMvc.perform(MockMvcRequestBuilders
      .get("/superhero/1"))

      .andExpect(status().isOk)
      .andExpect(content().json(ANY_SUPERHERO.toJson(), true))
  }

  @Test
  @WithMockUser("USER")
  fun `should return a 404 if the id not exist`() {
    every { superHeroRepository[any()] } returns None.right()

    mockMvc.perform(MockMvcRequestBuilders
      .get("/superhero/1"))

      .andExpect(status().isNotFound)
  }

  @Test
  @WithMockUser(roles = ["ADMIN"])
  fun `should return the superhero created if the values are correct`() {
    every { superHeroRepository.addSuperHero(any()) } returns ANY_SUPERHERO.right()

    mockMvc.perform(MockMvcRequestBuilders
      .post("/superhero")
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .content(ANY_NEW_SUPERHERO.toJson()))

      .andExpect(status().isCreated)
      .andExpect(content()
        .json(ANY_SUPERHERO.toJson(), true))
  }

  @Test
  @WithMockUser(roles = ["USER"])
  fun `should return the unauthorized if the user is not admin`() {
    every { superHeroRepository.addSuperHero(any()) } returns ANY_SUPERHERO.right()

    mockMvc.perform(MockMvcRequestBuilders
      .post("/superhero")
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .content(ANY_NEW_SUPERHERO.toJson()))

      .andExpect(status().isForbidden)
  }

  @Test
  @WithMockUser(roles = ["ADMIN"])
  fun `should return an error if the new superhero is invalid`() {
    mockMvc.perform(MockMvcRequestBuilders
      .post("/superhero")
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .content(WRONG_NEW_SUPERHERO))

      .andExpect(status().isUnprocessableEntity)
  }

  @WithMockUser(roles = ["ADMIN"])
  fun `should return sucess if the new superhero is deleted`() {
    every { superHeroRepository.delete(any()) } returns ANY_SUPERHERO.id.right()
    mockMvc.perform(
      MockMvcRequestBuilders.delete("/superhero/1"))

      .andExpect(status().isNoContent)
  }
}

private fun <T> T.toJson(): String {
  val objectMapper = ObjectMapper()
  return objectMapper.writeValueAsString(this)
}