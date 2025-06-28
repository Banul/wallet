package com.banulsoft.wallet.portfoliooutbox.application;

import java.util.List;

public record PortfolioCreateCommand(List<AssetCreateCommand> assets) {
}
