package com.example.backend.http.model

import com.example.backend.http.entity.DetectorEntity

class DetectorModel
{
    var sensorId: Int = 0
    var name: String = ""

    fun toModel(detectors: DetectorEntity): DetectorModel {
        val model = DetectorModel()
        model.sensorId = detectors.sensorId
        model.name = detectors.name
        return model
    }

    override fun toString(): String {
        return "$sensorId - $name"
    }
}