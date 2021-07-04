package com.example.backend.http.entity

import com.example.backend.CoAP.Sensor
import javax.persistence.*

@Entity
@Table(name = "detectors")
data class DetectorEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_id")
    val sensorId: Int = 0,

    val name: String = "",

    @OneToMany(mappedBy = "detector", cascade = [CascadeType.ALL])
    val sensors: List<Sensor>? = null

)