package com.banulsoft.wallet.portfoliodraft.web;

import com.banulsoft.wallet.portfoliodraft.application.PortfolioDraftFacade;
import com.banulsoft.wallet.portfoliodraft.domain.PortfolioDraft;
import com.banulsoft.wallet.portfoliodraft.domain.PortfolioDraftId;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/portfolio-draft")
@RequiredArgsConstructor
class PortfolioDraftController {
    private final PortfolioDraftFacade portfolioDraftFacade;

    @GetMapping("/{id}")
    public PortfolioDraftResponseDto findById(@PathVariable("id") UUID id) {
        PortfolioDraft portfolioDraft = portfolioDraftFacade.findById(new PortfolioDraftId(id));
        Set<DraftAssetResponseDto> assets = portfolioDraft.getAssetsCreationRequests()
                .stream()
                .map(x -> new DraftAssetResponseDto(x.ticker(), x.amount()))
                .collect(Collectors.toSet());

        return new PortfolioDraftResponseDto(portfolioDraft.getId(), portfolioDraft.getName(), assets);
    }
}