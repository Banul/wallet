package com.banulsoft.wallet.assetdetails.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface AssetDetailsJpaRepository extends JpaRepository<AssetDetailsEntity, UUID> {
}
