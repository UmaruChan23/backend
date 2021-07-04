package com.example.backend.http.repo

import com.example.backend.http.entity.DetectorEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DetectorsRepo: JpaRepository<DetectorEntity, Int> {
    fun findByName(name: String): DetectorEntity?
}