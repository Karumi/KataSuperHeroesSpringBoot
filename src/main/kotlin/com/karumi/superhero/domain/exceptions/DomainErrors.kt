// ktlint-disable filename
package com.karumi.superhero.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
object NotFound : RuntimeException("Superhero not found")
object DbStorageError : RuntimeException("Database connection error")