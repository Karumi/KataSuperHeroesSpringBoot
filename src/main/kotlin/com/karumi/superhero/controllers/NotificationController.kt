package com.karumi.superhero.controllers

import com.karumi.superhero.domain.usecase.GetSuperHeroNotifications
import com.karumi.superhero.domain.usecase.SuperHeroAlert
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body

@Controller
class NotificationController {

  @Bean
  fun routes(
    getSuperHeroNotifications: GetSuperHeroNotifications
  ) =
    RouterFunctions.route()
      .GET("/notifications") { notifications(it, getSuperHeroNotifications) }
      .build()

  private fun notifications(
    req: ServerRequest,
    getSuperHeroNotifications: GetSuperHeroNotifications
  ) =
    ServerResponse.ok()
      .contentType(MediaType.TEXT_EVENT_STREAM)
      .body<SuperHeroAlert>(getSuperHeroNotifications())
}