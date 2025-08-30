package com.banulsoft.wallet.portfoliodraft.infrastructure;

import com.banulsoft.wallet.portfolio.shared.AssetCreateCommand;
import com.banulsoft.wallet.portfoliodraft.domain.DraftStatus;
import com.banulsoft.wallet.shared.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

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

    public List<AssetCreateCommand> getAssetsCreationRequests() {
        return assetsCreationRequests;
    }

    public void setAssetsCreationRequests(List<AssetCreateCommand> assetsCreationRequests) {
        this.assetsCreationRequests = assetsCreationRequests;
    }

    public DraftStatus getStatus() {
        return status;
    }

    public void setStatus(DraftStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
