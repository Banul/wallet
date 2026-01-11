package com.banulsoft.wallet

import com.github.tomakehurst.wiremock.client.WireMock
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
abstract class BaseIntegrationTest extends Specification {

    @Shared
    static final PostgreSQLContainer postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15-alpine"))
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")

    @Shared
    static final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.5.0"))

    @Shared
    static final GenericContainer wiremock = new GenericContainer<>("wiremock/wiremock:2.35.0")
            .withExposedPorts(8080)

    static {
        postgres.start()
        kafka.start()
        wiremock.start()

        System.setProperty("spring.datasource.url", postgres.getJdbcUrl())
        System.setProperty("spring.datasource.username", postgres.getUsername())
        System.setProperty("spring.datasource.password", postgres.getPassword())
        System.setProperty("spring.datasource.driver-class-name", "org.postgresql.Driver")
        System.setProperty("spring.jpa.database-platform", "org.hibernate.dialect.PostgreSQLDialect")
        System.setProperty("spring.kafka.bootstrap-servers", kafka.getBootstrapServers())

        println "Postgres: ${postgres.getJdbcUrl()}"
        println "Kafka: ${kafka.getBootstrapServers()}"
        println "WireMock: ${wiremock.getHost()}:${wiremock.getMappedPort(8080)}"
    }

    def setupSpec() {
        WireMock.configureFor(wiremock.getHost(), wiremock.getMappedPort(8080))
    }

    def cleanup() {
        WireMock.reset()
    }

    def cleanupSpec() {
        postgres.stop()
        kafka.stop()
        wiremock.stop()
    }
}