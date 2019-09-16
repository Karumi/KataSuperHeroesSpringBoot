// ktlint-disable filename
package com.karumi.superhero.controllers.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
object NotFound : RuntimeException("Superhero not found")