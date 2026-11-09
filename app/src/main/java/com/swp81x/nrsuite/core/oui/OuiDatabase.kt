package com.swp81x.nrsuite.core.oui

data class OuiVendor(
    val ouiPrefix: String,
    val vendor: String,
)

data class MacLookupResult(
    val normalizedMac: String,
    val ouiPrefix: String?,
    val vendor: String?,
    val locallyAdministered: Boolean,
    val multicast: Boolean,
    val broadcast: Boolean,
    val databaseReady: Boolean,
)

sealed interface OuiDatabaseStatus {
    data object Checking : OuiDatabaseStatus
    data object NotDownloaded : OuiDatabaseStatus
    data object Downloading : OuiDatabaseStatus
    data class Ready(
        val vendorCount: Int,
        val updatedAt: String,
    ) : OuiDatabaseStatus
    data class Error(val message: String) : OuiDatabaseStatus
}
