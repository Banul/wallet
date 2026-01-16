package com.banulsoft.wallet.portfoliodraft.infrastructure;

import com.banulsoft.wallet.portfolio.shared.AssetCreateCommand;
import com.banulsoft.wallet.portfoliodraft.domain.DraftStatus;
import com.banulsoft.wallet.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Table(name = "portfolio_draft")
public class PortfolioDraftEntity extends BaseEntity {
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "assets_creation_requests")
    // todo - make this field private (should not come from shared language)
    private List<AssetCreateCommand> assetsCreationRequests;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DraftStatus status;

    @Column(name = "name")
    private String name;

    public void setAssetsCreationRequests(List<AssetCreateCommand> assetsCreationRequests) {
        this.assetsCreationRequests = assetsCreationRequests;
    }

    public void setStatus(DraftStatus status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }
}
