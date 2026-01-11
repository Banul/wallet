package com.banulsoft.wallet.portfolio.infrastructure

import com.banulsoft.wallet.BaseIntegrationTest
import com.banulsoft.wallet.portfolio.application.PortfolioFacade
import com.banulsoft.wallet.portfolio.domain.Portfolio
import com.banulsoft.wallet.portfolio.shared.AssetCreateCommand
import com.banulsoft.wallet.portfolio.shared.PortfolioCreateCommand
import com.banulsoft.wallet.portfoliodraft.application.PortfolioDraftFacade
import com.banulsoft.wallet.portfoliodraft.domain.DraftStatus
import com.banulsoft.wallet.portfoliodraft.domain.PortfolioDraftId
import com.banulsoft.wallet.portfoliodraft.domain.PortfolioDraftPersistancePort
import com.banulsoft.wallet.portfoliodraft.domain.TickerExistenceInformation
import com.banulsoft.wallet.portfoliodraft.infrastructure.ExistingCompaniesService
import org.springframework.beans.factory.annotation.Autowired

class PortfolioCreatorJobTest extends BaseIntegrationTest {

    @Autowired
    PortfolioDraftFacade portfolioDraftFacade

    @Autowired
    PortfolioFacade portfolioFacade

    @Autowired
    PortfolioCreatorJob portfolioCreatorJob

    @Autowired
    ExistingCompaniesService existingCompaniesService

    @Autowired
    PortfolioDraftPersistancePort port

    def "should create portfolios from valid drafts"() {
        given: "a valid draft exists in database"
          existingCompaniesService.handleTicker(new TickerExistenceInformation("XTB.WA", "OK", "PL", "Fintech", "Fintech"))
          def draftId = portfolioDraftFacade.create(new PortfolioCreateCommand(
                  List.of(new AssetCreateCommand("XTB.WA", 100)),
                  "Dividend Portfolio",
          ))

        when: "portfolio creation is requested"
          portfolioCreatorJob.createPortfolios()

        then: "portfolio is created"
          List<Portfolio> portfolios = portfolioFacade.findAll()
          portfolios.size() == 1
          portfolios[0].name == "Dividend Portfolio"
          portfolios[0].getPositions().size() == 1

        and: "draft is marked as created"
          portfolioDraftFacade.findPendingDrafts().isEmpty()
          def processedDraft = portfolioDraftFacade.findById(new PortfolioDraftId(draftId))
          processedDraft.status == DraftStatus.CREATED
    }

    def "should not process invalid drafts"() {
        given: "a draft with unknown ticker"
          portfolioDraftFacade.create(new PortfolioCreateCommand(
                  List.of(new AssetCreateCommand("NON_EXISTENT_TICKER", 50)),
                  "Random portfolio 12345",
          ))

        when: "job is executed"
          portfolioCreatorJob.createPortfolios()

        then: "no portfolio is created"
          portfolioFacade.findAll().stream()
                  .filter(x -> (x.getName() == "Random portfolio 12345"))
                  .toList()
                  .isEmpty()
    }
}