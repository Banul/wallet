package com.banulsoft.wallet.portfoliodraft.web

import com.banulsoft.wallet.BaseIntegrationTest
import com.banulsoft.wallet.portfolio.web.AssetCreateDto
import com.banulsoft.wallet.portfolio.web.PortfolioClient
import com.banulsoft.wallet.portfolio.web.PortfolioCreateDto
import com.banulsoft.wallet.portfoliodraft.domain.PortfolioDraftId
import org.springframework.beans.factory.annotation.Autowired

class PortfolioIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private PortfolioClient portfolioClient

    @Autowired
    private PortfolioDraftClient portfolioDraftClient

    def "should create draft firstly after user requests portfolio creation"() {
        given:
          def dto = new PortfolioCreateDto("somePortfolio", [new AssetCreateDto("SNT.WA", 10)])

        when: 'portfolio creation is requested'
          UUID draftId = portfolioClient.createPortfolio(dto)

        then:
          PortfolioDraftResponseDto draft = portfolioDraftClient.getDraft(new PortfolioDraftId(draftId))
          draft.id() != null
          draft.assets().size() == 1
          draft.name() != null
    }
}