package com.example.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IoTBackendApplication

fun main(args: Array<String>) {
	runApplication<IoTBackendApplication>(*args)
}
