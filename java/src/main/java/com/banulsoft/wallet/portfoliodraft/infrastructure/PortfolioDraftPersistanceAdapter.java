package com.banulsoft.wallet.portfoliodraft.infrastructure;

import com.banulsoft.wallet.portfoliodraft.domain.PortfolioDraft;
import com.banulsoft.wallet.portfoliodraft.domain.PortfolioDraftPersistancePort;
import com.banulsoft.wallet.portfoliodraft.domain.DraftStatus;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class PortfolioDraftPersistanceAdapter implements PortfolioDraftPersistancePort {
    private final PortfolioDraftJpaRepository portfolioDraftJpaRepository;

    public PortfolioDraftPersistanceAdapter(PortfolioDraftJpaRepository portfolioDraftJpaRepository) {
        this.portfolioDraftJpaRepository = portfolioDraftJpaRepository;
    }

    @Override
    public PortfolioDraft save(PortfolioDraft portfolioDraft) {
        PortfolioDraftEntity portfolioDraftEntity = new PortfolioDraftEntity();
        portfolioDraftEntity.setName(portfolioDraft.getName());
        portfolioDraftEntity.setStatus(DraftStatus.CREATED);
        portfolioDraftEntity.setAssetsCreationRequests(portfolioDraft.getAssetsCreationRequests());
        PortfolioDraftEntity entity = portfolioDraftJpaRepository.save(portfolioDraftEntity);
        return new PortfolioDraft(entity.getId(), entity.getName(), entity.getAssetsCreationRequests());
    }

    @Override
    public Set<PortfolioDraft> findPending() {
        return portfolioDraftJpaRepository.findPending()
                .stream().map(x -> new PortfolioDraft(x.getId(), x.getName(), x.getAssetsCreationRequests()))
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<PortfolioDraft> findById(UUID id) {
        return portfolioDraftJpaRepository.findById(id)
                .map(x -> new PortfolioDraft(x.getId(), x.getName(), x.getAssetsCreationRequests()));
    }
}
