package com.example.backend.http.model

import com.example.backend.CoAP.Sensor

class MetricsModel {
    var sensorId: Int = 0
    var timestamp: Long = 0
    var value: Short = 0

    fun toModel(sensor: Sensor): MetricsModel {
        val model = MetricsModel()
        model.sensorId = sensor.sensorId
        model.timestamp = sensor.timestamp
        model.value = sensor.value
        return model
    }
}