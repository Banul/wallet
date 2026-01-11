package com.banulsoft.wallet.portfoliodraft.infrastructure;

import com.banulsoft.wallet.portfoliodraft.domain.TickerExistenceInformation;
import com.banulsoft.wallet.shared.kafka.TickerDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExistingCompaniesService {
    private final JdbcTemplate jdbcTemplate;

    public void handleTicker(TickerExistenceInformation information) {
        boolean tickerExists = Objects.equals(information.getStatus(), "OK");
        String sql = "INSERT INTO searched_companies (id, ticker, does_exist) VALUES (?, ?, ?) ON CONFLICT (ticker) DO NOTHING";
        jdbcTemplate.update(sql, UUID.randomUUID(), information.getTicker(), tickerExists);
        if (tickerExists) {
            String sql2 = "INSERT INTO company_details (id, ticker, country, industry, sector, created_date) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql2, UUID.randomUUID(), information.getTicker(), information.getCountry(), information.getIndustry(), information.getSector(), Timestamp.from(Instant.now()));
        }
    }

    public boolean allNamesExist(Set<String> tickers) {
        if (tickers.isEmpty()) {
            return true;
        }

        List<SearchedCompany> companies = findByTickers(tickers);
        if (tickers.size() != companies.size()) {
            // at least one company not found in list
            return false;
        }
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