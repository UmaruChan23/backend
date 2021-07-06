package com.example.backend.http.controller

import com.example.backend.http.model.DetectorModel
import com.example.backend.http.model.MetricsModel
import com.example.backend.http.service.MetricsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api")
class MetricsController(private val service: MetricsService) {

    @GetMapping
    fun getOneForHour(@RequestParam id: Int): ResponseEntity<List<MetricsModel>> {
        return ResponseEntity.ok(service.listByTimestamp(id))
    }

    @GetMapping("/sensors")
    fun getAll(): ResponseEntity<List<DetectorModel>> {
        return ResponseEntity.ok(service.listOfSensors())
    }

    @PostMapping
    fun insertSensor(@RequestBody name: String): ResponseEntity<Int> {
        return ResponseEntity.ok(service.insertSensor(name))
    }

}