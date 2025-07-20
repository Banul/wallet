package com.banulsoft.wallet.portfoliooutbox.web;

import com.banulsoft.wallet.portfolio.domain.Portfolio;
import com.banulsoft.wallet.portfoliooutbox.domain.AssetsCreationRequest;
import com.banulsoft.wallet.position.Position;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

record PortfolioResponseDto(String name, BigDecimal value) { }
