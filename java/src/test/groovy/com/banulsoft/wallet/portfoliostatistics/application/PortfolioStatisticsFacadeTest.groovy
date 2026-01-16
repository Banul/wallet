package com.banulsoft.wallet.portfoliostatistics.application

import com.banulsoft.wallet.portfolio.domain.PortfolioId
import com.banulsoft.wallet.portfolio.domain.TickerValue
import com.banulsoft.wallet.portfoliostatistics.domain.PortfolioStatistics
import com.banulsoft.wallet.portfoliovaluation.application.PortfolioValuationFacade
import com.banulsoft.wallet.portfoliovaluation.domain.PortfolioValuation
import com.banulsoft.wallet.portfoliovaluation.domain.PortfolioValuationBaseInformation
import com.banulsoft.wallet.shared.CountryCode
import com.banulsoft.wallet.shared.Ticker
import com.banulsoft.wallet.stockinformation.application.StockInformationFacade
import com.banulsoft.wallet.stockinformation.domain.StockInformation
import com.banulsoft.wallet.stockvaluation.domain.Currency
import com.banulsoft.wallet.stockvaluation.domain.Valuation
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title("Portfolio Statistics Logic")
class PortfolioStatisticsFacadeTest extends Specification {

    def stockInformationFacade = Mock(StockInformationFacade)
    def portfolioValuationFacade = Mock(PortfolioValuationFacade)

    @Subject
    def facade = new PortfolioStatisticsFacade(stockInformationFacade, portfolioValuationFacade)

    def "should calculate correct country and sector shares for a diversified portfolio"() {
        given: "a portfolio containing assets from different countries (USA, Poland) and sectors"
          def id = new PortfolioId(UUID.randomUUID())
          def apple = new Ticker("AAPL")
          def cdProjekt = new Ticker("CDR")

          // Portfolio Composition: 60% Apple, 40% CD Projekt
          def tickerValues = [
                  tickerValue(apple, 600.0),
                  tickerValue(cdProjekt, 400.0)
          ]
          def totalValue = BigDecimal.valueOf(1000.0)

        and: "reference information (country, sector) is available for these assets"
          def stockInfos = [
                  new StockInformation(apple, "Technology", "Consumer Electronics", "United States"),
                  new StockInformation(cdProjekt, "Gaming", "Video Games", "Poland")
          ] as Set

        when: "calculating statistics for the portfolio"
          def result = facade.calculateStatisticsForPortfolio(id)

        then: "current values and stock metadata are fetched from dependencies"
          1 * portfolioValuationFacade.valuesPerTicker(id) >> tickerValues
          1 * portfolioValuationFacade.getValuationForPortfolio(id.id()) >> valuationResponse(totalValue)
          1 * stockInformationFacade.findByTickers({ it.containsAll([apple, cdProjekt]) }) >> stockInfos

        and: "the country shares breakdown accurately reflects the value distribution"
          result.countryShares.size() == 2

          def usShare = result.countryShares.find { it.countryCode() == CountryCode.USA }
          usShare.percent().value() == BigDecimal.valueOf(60.00)

          def plShare = result.countryShares.find { it.countryCode() == CountryCode.POL }
          plShare.percent().value() == BigDecimal.valueOf(40.00)

        and: "the sector shares breakdown accurately reflects the value distribution"
          result.industryShares.size() == 2

          def techShare = result.industryShares.find { it.sectorName().industry().toLowerCase() == "technology" }
          techShare.percent().value() == BigDecimal.valueOf(60.00)
          def gamingShare = result.industryShares.find { it.sectorName().industry().toLowerCase() == "other" }
          gamingShare.percent().value() == BigDecimal.valueOf(40.00)
    }

    def "should ignore assets that lack stock information or have unknown country codes"() {
        given: "a portfolio with known and unknown assets"
          def id = new PortfolioId(UUID.randomUUID())
          def knownTicker = new Ticker("IBM")
          def unknownTicker = new Ticker("MYSTERY")
          def missingInfoTicker = new Ticker("MISSING")

          def tickerValues = [
                  tickerValue(knownTicker, 100.0),
                  tickerValue(unknownTicker, 100.0), // Has info, but country Unknown
                  tickerValue(missingInfoTicker, 100.0) // No info from facade
          ]
          def totalValue = BigDecimal.valueOf(300.0)

        and: "stock information is returned only partially"
          def stockInfos = [
                  new StockInformation(knownTicker, "IT Services", "Consulting", "United States"),
                  new StockInformation(unknownTicker, "Magic", "Dark Arts", "Atlantis") // "Atlantis" -> UNKNOWN
          ] as Set

        when: "calculating statistics"
          PortfolioStatistics result = facade.calculateStatisticsForPortfolio(id)

        then: "dependencies are called"
          1 * portfolioValuationFacade.valuesPerTicker(id) >> tickerValues
          1 * portfolioValuationFacade.getValuationForPortfolio(id.id()) >> valuationResponse(totalValue)
          1 * stockInformationFacade.findByTickers(_) >> stockInfos

        and: "only the valid asset is included in the country stats"
          result.countryShares.size() == 1
          result.countryShares[0].countryCode() == CountryCode.USA
          result.countryShares[0].percent().value() == BigDecimal.valueOf(33.33)

        and: "unknown countries are excluded"
          !result.countryShares.any { it.countryCode() == CountryCode.UNKNOWN }
    }

    def "should handle zero total portfolio value gracefully"() {
        given: "a portfolio with zero total value"
          def id = new PortfolioId(UUID.randomUUID())
          def ticker = new Ticker("PENNY")

          def tickerValues = [tickerValue(ticker, 0.0)]
          def totalValue = BigDecimal.ZERO

          def stockInfos = [
                  new StockInformation(ticker, "Tech", "Hardware", "United States")
          ] as Set

        when: "calculating statistics"
          def result = facade.calculateStatisticsForPortfolio(id)

        then: "dependencies return zero value"
          1 * portfolioValuationFacade.valuesPerTicker(id) >> tickerValues
          1 * portfolioValuationFacade.getValuationForPortfolio(id.id()) >> valuationResponse(totalValue)
          1 * stockInformationFacade.findByTickers(_) >> stockInfos

        and: "percentages are calculated as zero to avoid division by zero error"
          result.countryShares.size() == 1
          result.countryShares[0].percent().value() == BigDecimal.ZERO
    }

    private TickerValue tickerValue(Ticker ticker, double amount) {
        return new TickerValue(ticker, BigDecimal.valueOf(amount))
    }

    private PortfolioValuation valuationResponse(BigDecimal totalAmount) {
        def valuation = new Valuation(totalAmount, Currency.PLN)
        def baseInfo = new PortfolioValuationBaseInformation(new PortfolioId(UUID.randomUUID()), "dummy")
        return new PortfolioValuation(valuation, baseInfo)
    }
}