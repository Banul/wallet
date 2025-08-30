package com.banulsoft.wallet.portfolio.shared;

import java.util.List;

public record PortfolioCreateCommand(List<AssetCreateCommand> assets, String name) {
}
