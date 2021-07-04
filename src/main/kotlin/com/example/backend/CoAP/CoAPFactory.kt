package com.example.backend.CoAP

import com.example.backend.http.repo.DetectorsRepo
import com.example.backend.http.repo.MetricsRepo
import com.mbed.coap.exception.CoapCodeException
import com.mbed.coap.packet.Code
import com.mbed.coap.server.CoapExchange
import com.mbed.coap.server.CoapHandler
import com.mbed.coap.server.CoapServer
import com.mbed.coap.utils.CoapResource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.nio.ByteBuffer
import java.util.*
import kotlin.experimental.xor

@Configuration
class CoAPFactory(@Autowired val res: SimpleCoapResource) {

    @Bean
    fun createServer() {
        val server: CoapServer = CoapServer.builder().transport(5683).build()
        val handler: CoapHandler = res
        server.addRequestHandler("/api/metrics", handler)
        server.start()
        return
    }

    @Component
    class SimpleCoapResource(@Autowired val repo: MetricsRepo, @Autowired val detectorsRepo: DetectorsRepo, @Value("\${api.key:def}")
    val key: String) :
        CoapResource() {

        private var body = "ok"

        @Throws(CoapCodeException::class)
        override fun get(ex: CoapExchange) {
            ex.setResponseBody("")
            ex.setResponseCode(Code.C205_CONTENT)
            ex.sendResponse()
        }

        @Throws(CoapCodeException::class)
        override fun put(ex: CoapExchange) {
            try {
                body = ex.requestBodyString.trim()

                val decodedBytes = Base64.getDecoder().decode(body)
                val bytesToSave = decode(decodedBytes, key)
                val decoded = Base64.getDecoder().decode(bytesToSave)

                repo.save(bodyParser(decoded))
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

            ex.setResponseCode(Code.C204_CHANGED)
            ex.sendResponse()
        }

        fun decode(pText: ByteArray, pKey: String): String {
            val res = ByteArray(pText.size)
            val key = pKey.toByteArray()
            for (i in pText.indices) {
                res[i] = (pText[i] xor key[i % key.size])
            }
            return String(res)
        }

        private fun bodyParser(metrics: ByteArray): Sensor {
            val id: ByteArray = byteArrayOf(metrics[1], metrics[2], metrics[3], metrics[4])
            val timestamp: ByteArray = byteArrayOf(
                metrics[5],
                metrics[6],
                metrics[7],
                metrics[8],
                metrics[9],
                metrics[10],
                metrics[11],
                metrics[12]
            )
            val value: ByteArray = byteArrayOf(metrics[14], metrics[13])

            return Sensor(
                0,
                metrics[0].toInt(),
                bytesToInt(id),
                bytesToLong(timestamp),
                bytesToShort(value),
                detectorsRepo.getById(bytesToInt(id))
            )
        }

        private fun bytesToLong(bytes: ByteArray?): Long {
            val buffer: ByteBuffer = ByteBuffer.allocate(java.lang.Long.BYTES)
            buffer.put(bytes)
            buffer.flip()
            return buffer.long
        }

        private fun bytesToInt(bytes: ByteArray?): Int {
            val buffer: ByteBuffer = ByteBuffer.allocate(Integer.BYTES)
            buffer.put(bytes)
            buffer.flip()
            return buffer.int
        }

        private fun bytesToShort(bytes: ByteArray?): Short {
            val buffer: ByteBuffer = ByteBuffer.allocate(java.lang.Short.BYTES)
            buffer.put(bytes)
            buffer.flip()
            return buffer.short
        }
    }
}