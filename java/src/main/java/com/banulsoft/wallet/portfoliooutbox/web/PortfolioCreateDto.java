package com.banulsoft.wallet.portfoliooutbox.web;

import java.util.List;

record PortfolioCreateDto(String portfolioName, List<AssetCreateDto> assets) {

}
