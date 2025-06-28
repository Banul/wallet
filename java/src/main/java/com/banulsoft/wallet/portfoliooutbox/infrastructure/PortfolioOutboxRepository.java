package com.banulsoft.wallet.portfoliooutbox.infrastructure;

import com.banulsoft.wallet.portfolio.domain.Portfolio;
import com.banulsoft.wallet.portfoliooutbox.domain.PortfolioOutbox;

public interface PortfolioOutboxRepository {
    PortfolioOutbox save(PortfolioOutbox portfolio);
}
