package com.banulsoft.wallet

import com.github.tomakehurst.wiremock.client.WireMock
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
abstract class BaseIntegrationTest extends Specification {

    static final PostgreSQLContainer postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15-alpine"))
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")

    static final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.5.0"))

    static final GenericContainer wiremock = new GenericContainer<>("wiremock/wiremock:2.35.0")
            .withExposedPorts(8080)

    static {
        [postgres, kafka, wiremock].parallelStream().forEach { it.start() }
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl)
        registry.add("spring.datasource.username", postgres::getUsername)
        registry.add("spring.datasource.password", postgres::getPassword)
        registry.add("spring.datasource.driver-class-name", { "org.postgresql.Driver" })
        registry.add("spring.jpa.database-platform", { "org.hibernate.dialect.PostgreSQLDialect" })
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers)
    }

    def setupSpec() {
        WireMock.configureFor(wiremock.getHost(), wiremock.getMappedPort(8080))
    }

    def cleanup() {
        WireMock.reset()
    }
}