package com.banulsoft.wallet.portfoliodraft.domain;

/**
 * Describes whether we can create portfolio from draft
 */
public enum DraftStatus {
    /**
     * already processed and we have valid portfolio from this draft existing
     */
    CREATED,

    /**
     * is in progress, still waiting for response from external service
     */
    PENDING,

    /**
     * we can create portfolio from it
     */
    READY_FOR_PROCESSING
}
