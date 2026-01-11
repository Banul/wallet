package com.banulsoft.wallet.portfolio.web;

import java.util.List;

public record PortfolioCreateDto(String name, List<AssetCreateDto> assets) {

}
