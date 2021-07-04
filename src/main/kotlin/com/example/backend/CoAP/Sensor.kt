package com.example.backend.CoAP

import com.example.backend.http.entity.DetectorEntity
import javax.persistence.*

@Entity
@Table(name = "metrics")
data class Sensor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val version: Int = 1,

    @Column(name = "sensor_id")
    val sensorId: Int = 0,

    @Column(name = "time_stamp")
    val timestamp: Long = 0,

    @Column(name = "sensor_value")
    val value: Short = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    private val detector: DetectorEntity

)