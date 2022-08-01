package com.github.gyangustar.zhaicaipuserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(exclude=[MongoAutoConfiguration::class, MongoDataAutoConfiguration::class])
class ZhaicaipuServerApplication

fun main(args: Array<String>) {
    runApplication<ZhaicaipuServerApplication>(*args)
}
