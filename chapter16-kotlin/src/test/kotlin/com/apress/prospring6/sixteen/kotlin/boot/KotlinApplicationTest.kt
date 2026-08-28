package com.apress.prospring6.sixteen.kotlin.boot

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.resttestclient.TestRestTemplate
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import java.net.URI
import java.net.URISyntaxException

@ActiveProfiles("test")
@AutoConfigureTestRestTemplate
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KotlinApplicationTest (@Autowired val restTemplate: TestRestTemplate,
    @Value(value = "\${local.server.port}") val port: Int) {

    var logger: Logger = LoggerFactory.getLogger(javaClass)

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            println(">> Setup")
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            println(">> TearDown")
        }
    }

    @Test
    fun `find all singers`() {
        val singers: Array<Singer> = restTemplate.getForObject("/singer", Array<Singer>::class.java)!!
        Assertions.assertTrue(singers.size >= 15)
        for (it in singers) logger.info(it.toString())
    }

    @Test
    @Throws(URISyntaxException::class)
    fun `can't find Singer by id`() {
        logger.info("--> Testing retrieve a singer by id : 99")

        val req: RequestEntity<Singer> = RequestEntity<Singer>(HttpMethod.GET,
            URI("http://localhost:$port/singer/99"))
        val response: ResponseEntity<Singer> = restTemplate.exchange(req, Singer::class.java)

        Assertions.assertAll("testNegativeFindById",
            Executable { Assertions.assertEquals(HttpStatus.NOT_FOUND, response.statusCode) },
            Executable { Assertions.assertNull(response.body?.firstName)},
            Executable { Assertions.assertNull(response.body?.lastName)})
    }
}
