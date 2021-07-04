package com.example.backend.http.repo

import com.example.backend.CoAP.Sensor
import org.springframework.data.jpa.repository.JpaRepository

interface MetricsRepo: JpaRepository<Sensor, Int> {
    fun findBySensorId(id: Int): ArrayList<Sensor>
}