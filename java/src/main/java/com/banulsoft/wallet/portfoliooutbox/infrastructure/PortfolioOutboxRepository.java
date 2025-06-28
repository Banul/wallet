package com.banulsoft.wallet.portfoliooutbox.infrastructure;

import com.banulsoft.wallet.portfoliooutbox.domain.PortfolioOutbox;

public interface PortfolioOutboxRepository {
    PortfolioOutboxEntity save(PortfolioOutbox portfolio);
}
