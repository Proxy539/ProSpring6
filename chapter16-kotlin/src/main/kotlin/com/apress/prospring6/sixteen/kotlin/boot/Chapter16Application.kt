package com.apress.prospring6.sixteen.kotlin.boot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.core.env.AbstractEnvironment

@SpringBootApplication
class Chapter16Application {

    fun main(args: Array<String>) {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "dev")
        runApplication<Chapter16Application>(*args)
    }
}