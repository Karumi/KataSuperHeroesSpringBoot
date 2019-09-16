package com.karumi.superhero.controllers.common

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException
import java.util.ArrayList
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

  @ExceptionHandler(InvalidDefinitionException::class)
  fun handleConflict(
    ex: InvalidDefinitionException,
    request: WebRequest
  ): ResponseEntity<ErrorResponse> {
    val details = ArrayList<String>()
    details.add(ex.message ?: "error reading input")
    val error = ErrorResponse("Server Error", details)
    return ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY)
  }
}

data class ErrorResponse(
  val message: String,
  val details: ArrayList<String>
)