package com.example.backend.http.exceptionHandler

import com.example.backend.http.exceptions.SensorAlreadyExistException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*


@ControllerAdvice
class AdviceExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [(SensorAlreadyExistException::class)])
    fun insertHandler(ex: Exception, request: WebRequest): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(
            Date(),
            "Sensor Already Exist",
            ex.message!!
        )
        return  ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [(Exception::class)])
    fun defaultHandler(ex: Exception, request: WebRequest): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(
            Date(),
            "Validation Failed",
            ex.message!!
        )
        return  ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }
}
