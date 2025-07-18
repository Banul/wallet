package com.banulsoft.wallet.stockvaluation.infrastructure;

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
        List<String> strings = jdbcTemplate.queryForList("select * from searched_companies ad", String.class);
    }
}
