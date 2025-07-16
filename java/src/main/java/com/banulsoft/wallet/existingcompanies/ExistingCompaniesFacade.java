package com.banulsoft.wallet.existingcompanies;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExistingCompaniesFacade {
    private final JdbcTemplate jdbcTemplate;
    public boolean allNamesExist(Set<String> names) {
        if (names == null || names.isEmpty()) {
            return false;
        }

        String inSql = String.join(",", Collections.nCopies(names.size(), "?"));
        String sql = String.format("SELECT COUNT(*) FROM searched_companies WHERE ticker IN (%s) and does_exist is true", inSql);

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                names.toArray()
        );

        return count == names.size();
    }
}