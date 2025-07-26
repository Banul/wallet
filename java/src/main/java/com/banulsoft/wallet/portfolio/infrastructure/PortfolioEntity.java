package com.banulsoft.wallet.portfolio.infrastructure;

import com.banulsoft.wallet.portfolio.domain.Position;
import com.banulsoft.wallet.shared.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;


@Entity
@Table(name = "portfolio")
class PortfolioEntity extends BaseEntity {
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "positions")
    private Set<Position> positions;

    @Column(name = "name")
    private String name;

    public Set<Position> getPositions() {
        return positions;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
