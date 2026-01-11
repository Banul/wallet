package com.banulsoft.wallet.portfoliodraft.web;

import java.util.Set;
import java.util.UUID;

record PortfolioDraftResponseDto(UUID id, String name, Set<DraftAssetResponseDto> assets) {
}
