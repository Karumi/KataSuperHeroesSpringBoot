package com.karumi.superhero.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.karumi.superhero.domain.model.SuperHero
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@WebMvcTest(SuperHeroController::class)
class SuperHeroControllerTest(
  @Autowired val mockMvc: MockMvc
) {

  val ANY_SUPERHERO = SuperHero(name = "Wolverine")

  @Test
  fun `should return the list of superheroes when contains superheroes`() {
    mockMvc.perform(MockMvcRequestBuilders
      .get("/superhero"))

      .andExpect(status().isOk)
      .andExpect(content().json((listOf(ANY_SUPERHERO).toJson()), true))
  }
}

private fun <T> T.toJson(): String {
  val objectMapper = ObjectMapper()
  return objectMapper.writeValueAsString(this)
}