package com.banulsoft.wallet.portfolio.web;

import java.util.List;

record PortfolioCreateDto(String name, List<AssetCreateDto> assets) {

}
