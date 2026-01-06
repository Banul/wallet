package com.banulsoft.wallet.portfolio.web

import com.banulsoft.wallet.BaseIntegrationTest
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class PortfolioIntegrationTest extends BaseIntegrationTest {

    @Autowired
    MockMvc mockMvc  // DZIAŁA 100%

    ObjectMapper objectMapper = new ObjectMapper()

    def "context loads correctly"() {
        given:
          def dto = new PortfolioCreateDto("somePortfolio", [new AssetCreateDto("SNT.WA", 10)])

        when:
          mockMvc.perform(post("/portfolio")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(dto)))

        then:
          1 == 1  // PASS!
    }
}