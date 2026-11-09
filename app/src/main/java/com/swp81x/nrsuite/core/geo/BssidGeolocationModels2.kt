package com.swp81x.nrsuite.core.geo

// Pure display/formatting helpers kept for the UI-only build. No network
// lookups happen here; nothing is sent anywhere.

data class BssidGeolocationResult(
    val bssid: String,
    val latitude: Double,
    val longitude: Double,
    val accuracyMeters: Double?,
    val source: String,
)

fun normalizeBssid(input: String): String? {
    val compact = input.trim()
        .uppercase()
        .replace(":", "")
        .replace("-", "")
        .replace(".", "")
    if (compact.length != 12 || compact.any { it.digitToIntOrNull(16) == null }) return null
    val normalized = compact.chunked(2).joinToString(":")
    if (normalized == "00:00:00:00:00:00" || normalized == "FF:FF:FF:FF:FF:FF") return null
    return normalized
}
