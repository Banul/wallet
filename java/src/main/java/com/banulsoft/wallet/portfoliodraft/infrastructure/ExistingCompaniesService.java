package com.banulsoft.wallet.portfoliodraft.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExistingCompaniesService {
    private final JdbcTemplate jdbcTemplate;

    public boolean allNamesExist(Set<String> tickers) {
        if (tickers.isEmpty()) {
            return true;
        }

        List<SearchedCompany> companies = findByTickers(tickers);
        return companies.stream().allMatch(SearchedCompany::isDoesExist);
    }

    public List<SearchedCompany> findByTickers(Set<String> tickers) {
        if (tickers == null || tickers.isEmpty()) {
            return Collections.emptyList();
        }

        String placeholders = String.join(",", Collections.nCopies(tickers.size(), "?"));
        String sql = "SELECT id, ticker, does_exist FROM searched_companies WHERE ticker IN (" + placeholders + ")";

        Object[] params = tickers.toArray(new Object[0]);

        // todo - deprecated
        return jdbcTemplate.query(sql, params, rowMapper);
    }

    private final RowMapper<SearchedCompany> rowMapper = (rs, rowNum) -> {
        SearchedCompany c = new SearchedCompany();
        c.setId(rs.getObject("id", UUID.class));
        c.setTicker(rs.getString("ticker"));
        c.setDoesExist(rs.getBoolean("does_exist"));
        return c;
    };
}