package com.banulsoft.wallet.portfoliovaluationsnapshot.domain;

import java.util.List;

public interface ValuationSnapshotPersistencePort {
    void save(List<ValuationSnapshot> snapshots);
}