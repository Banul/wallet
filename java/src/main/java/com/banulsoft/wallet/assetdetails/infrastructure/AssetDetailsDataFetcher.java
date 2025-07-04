package com.banulsoft.wallet.assetdetails.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class AssetDetailsDataFetcher {
    private final JdbcTemplate jdbcTemplate;

    private void aaa() {
        // todo -> continue on this one
        List<String> strings = jdbcTemplate.queryForList("select * from tracked_companies ad", String.class);
    }
}
