package com.banulsoft.wallet.portfoliovaluation.application

import com.banulsoft.wallet.portfolio.application.PortfolioFacade
import com.banulsoft.wallet.portfolio.domain.Portfolio
import com.banulsoft.wallet.portfolio.domain.PortfolioId
import com.banulsoft.wallet.portfolio.domain.Position
import com.banulsoft.wallet.shared.Ticker
import com.banulsoft.wallet.stockvaluation.application.StockValuationFacade
import com.banulsoft.wallet.stockvaluation.domain.Currency
import com.banulsoft.wallet.stockvaluation.domain.StockValuation
import com.banulsoft.wallet.stockvaluation.domain.Valuation
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title("Portfolio Valuation Logic Tests")
class PortfolioValuationFacadeSpec extends Specification {

    def portfolioFacade = Mock(PortfolioFacade)
    def stockValuationFacade = Mock(StockValuationFacade)

    @Subject
    def facade = new PortfolioValuationFacade(portfolioFacade, stockValuationFacade)

    def "should calculate portfolio value correctly aggregating mixed currencies"() {
        given: "a portfolio exists with positions in local (PLN) and foreign (USD) currencies"
          def id = UUID.randomUUID()
          def cdr = new Ticker("CDR")
          def aapl = new Ticker("AAPL")

          def portfolio = portfolioWithPositions(id, [
                  position(cdr, 10.0),
                  position(aapl, 5.0)
          ])

        and: "current market valuations for these tickers are available"
          def valuations = [
                  valuation(cdr, 100, Currency.PLN),
                  valuation(aapl, 150, Currency.USD)
          ] as Set

        when: "calculating the total value for this portfolio"
          def result = facade.calculate(id)

        then: "data is fetched from the repository and valuation service"
          1 * portfolioFacade.findById(id) >> portfolio
          1 * stockValuationFacade.getValuationForTickers({ Set t -> t.containsAll([cdr, aapl]) }) >> valuations

        and: "the result represents the sum of all positions converted to base currency (PLN)"
          // Calculation: (10 * 100 * 1.0) + (5 * 150 * 3.65) = 1000 + 2737.5 = 3737.5
          result.price() == BigDecimal.valueOf(3737.5)
    }

    def "should return valuation summaries for all portfolios in the system"() {
        given: "multiple portfolios exist in the system"
          def id1 = UUID.randomUUID()
          def id2 = UUID.randomUUID()

          def p1 = emptyPortfolio(id1, "Retirement Fund")
          def p2 = emptyPortfolio(id2, "Speculative Assets")

        and: "the portfolio repository returns these portfolios"
          portfolioFacade.findAll() >> [p1, p2]
          portfolioFacade.findById(id1) >> p1
          portfolioFacade.findById(id2) >> p2
          stockValuationFacade.getValuationForTickers(_) >> Set.of()

        when: "requesting valuation summary for all portfolios"
          def result = facade.getValuationForAllPortfolios()

        then: "a list containing valuation data for exactly two portfolios is returned"
          result.size() == 2

        and: "the returned data correctly maps IDs to portfolio names"
          def val1 = result.find { it.baseInformation().portfolioId().id() == id1 }
          val1.baseInformation().portfolioName() == "Retirement Fund"

          def val2 = result.find { it.baseInformation().portfolioId().id() == id2 }
          val2.baseInformation().portfolioName() == "Speculative Assets"
    }

    def "should return granular value breakdown per individual ticker"() {
        given: "a portfolio containing a specific asset (KGHM)"
          def id = UUID.randomUUID()
          def kghm = new Ticker("KGH")
          def portfolio = portfolioWithPositions(id, [position(kghm, 10.0)])

        and: "market valuation for this asset is known"
          def marketValue = valuation(kghm, 50, Currency.PLN)

          portfolioFacade.findById(id) >> portfolio
          stockValuationFacade.getValuationForTickers(_) >> Set.of(marketValue)

        when: "requesting value breakdown per ticker for this portfolio"
          def result = facade.valuesPerTicker(new PortfolioId(id))

        then: "the result contains a list with calculated value for that specific ticker"
          result.size() == 1
          with(result.first()) {
              ticker() == kghm
              value() == BigDecimal.valueOf(500.0) // 10 units * 50 PLN
          }
    }

    def "should throw exception when encountering an unsupported currency"() {
        given: "a portfolio containing an asset"
          def id = UUID.randomUUID()
          def unknownTicker = new Ticker("UNKNOWN")
          def portfolio = portfolioWithPositions(id, [position(unknownTicker, 1.0)])

        and: "the asset valuation uses a currency not supported by the system"
          def badValuation = valuation(unknownTicker, 10, Currency.UNKNOWN)

          portfolioFacade.findById(id) >> portfolio
          stockValuationFacade.getValuationForTickers(_) >> Set.of(badValuation)

        when: "attempting to calculate portfolio value"
          facade.calculate(id)

        // todo - some better exception
        then: "exception is thrown due to missing exchange rate"
          thrown(IllegalStateException)
    }

    private Portfolio portfolioWithPositions(UUID id, List<Position> positions) {
        return new Portfolio(id, new HashSet<>(positions), "Test Portfolio")
    }

    private Portfolio emptyPortfolio(UUID id, String name) {
        return new Portfolio(id, Set.of(), name)
    }

    private Position position(Ticker ticker, double amount) {
        return new Position(ticker, amount)
    }

    private StockValuation valuation(Ticker ticker, double price, Currency currency) {
        return new StockValuation(ticker, new Valuation(BigDecimal.valueOf(price), currency))
    }
}