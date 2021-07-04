package com.example.backend.http.service

import com.example.backend.CoAP.Sensor
import com.example.backend.http.entity.DetectorEntity
import com.example.backend.http.exceptions.SensorAlreadyExistException
import com.example.backend.http.model.DetectorModel
import com.example.backend.http.model.MetricsModel
import com.example.backend.http.repo.DetectorsRepo
import com.example.backend.http.repo.MetricsRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

@Service
class MetricsService(@Autowired val repo: MetricsRepo, @Autowired val detectRepo: DetectorsRepo) {

    fun listByTimestamp(id: Int): List<MetricsModel> {
        val finds: ArrayList<Sensor> = repo.findBySensorId(id)
        val finalList: ArrayList<MetricsModel> = arrayListOf()
        val millis = Calendar.getInstance().timeInMillis
        val time = TimeUnit.MILLISECONDS.toSeconds(millis)
        for(one in finds) {
            if((time - one.timestamp) < 3600) {
                finalList.add(MetricsModel().toModel(one))
            }
        }
        return finalList
    }

    fun insertSensor(name: String): Int {
        if(detectRepo.findByName(name) == null){
            detectRepo.save(DetectorEntity(0, name))
            return detectRepo.findByName(name)!!.sensorId
        }
        throw SensorAlreadyExistException("the name is already taken")
    }

    fun listOfSensors(): ArrayList<DetectorModel> {
        val detectors: List<DetectorEntity> = detectRepo.findAll()
        val models: ArrayList<DetectorModel> = arrayListOf()
        for(detector in detectors) {
            models.add(DetectorModel().toModel(detector))
        }
        return models
    }



}