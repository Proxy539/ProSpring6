package com.apress.prospring6.sixteen.kotlin.boot

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
@ResponseBody
class RestErrorHandler {

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleBadRequest(ex: DataIntegrityViolationException): ResponseEntity<Any>? {
        return ResponseEntity.badRequest().body(ex.message)
    }
}