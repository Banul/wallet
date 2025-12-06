package com.banulsoft.wallet.portfoliooutbox.infrastructure;

/**
 * Internal status for need of tracking outbox pattern status
 */
enum OutboxProcessingStatus {
    UNPROCESSED,
    SENT,
    FAILED,
    PROCESSED,
    CONSUMED
}