package com.banulsoft.wallet.portfoliodraft.domain;

/**
 * Describes whether we can create portfolio from draft
 */
public enum DraftStatus {
    CREATED,
    // pending means sent to kafka
    PENDING,
    SUCCEEDED,
    FAILED
}
