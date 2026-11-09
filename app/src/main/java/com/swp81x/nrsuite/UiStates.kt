package com.swp81x.nrsuite

import android.net.Uri
import com.swp81x.nrsuite.core.log.LogLevel
import com.swp81x.nrsuite.core.wpa.Pcap22000Candidate
import com.swp81x.nrsuite.core.wpa.Pcap22000Export

// Plain UI-state holders. The original app computed these live from captured
// hardware data; this UI-only build keeps the shapes so the screens compile
// unchanged but never populates them from anything real.

data class ProbeObservation(
    val mac: String,
    val ssid: String,
    val vendor: String?,
    val channel: Int,
    val rssi: Int,
    val firstSeen: String,
    val lastSeen: String,
    val sightings: Int,
    val randomizedMac: Boolean,
)

data class SerialLogLine(
    val timestamp: String,
    val text: String,
    val level: LogLevel,
)

data class Pcap22000Input(
    val uri: Uri,
    val name: String,
)

data class Pcap22000UiState(
    val inputs: List<Pcap22000Input> = emptyList(),
    val candidates: List<Pcap22000Candidate> = emptyList(),
    val ssidOverrides: Map<String, String> = emptyMap(),
    val exportPreview: Pcap22000Export? = null,
    val analyzing: Boolean = false,
    val summary: String? = null,
    val error: String? = null,
    val lastExportCount: Int? = null,
    val lastExportName: String? = null,
)

data class WpsPixieLiveLogEntry(
    val time: String,
    val message: String,
)

data class WpsPixieLiveUiState(
    val scanning: Boolean = false,
    val targets: List<com.swp81x.nrsuite.core.wifi.NetworkTarget> = emptyList(),
    val wpsOnly: Boolean = true,
    val selectedTarget: com.swp81x.nrsuite.core.wifi.NetworkTarget? = null,
    val candidatePin: String = "",
    val candidateSource: com.swp81x.nrsuite.core.wps.WpsPinSource? = null,
    val running: Boolean = false,
    val runId: String? = null,
    val stage: String = "",
    val message: String = "",
    val material: com.swp81x.nrsuite.core.wps.WpsPixieCapturedMaterial? = null,
    val firstHalfValid: Boolean? = null,
    val recoveredPin: String? = null,
    val psk: String? = null,
    val error: String? = null,
    val locked: Boolean = false,
    val autoRetryUsed: Boolean = false,
    val lastAttemptCaptureOnly: Boolean = false,
    val retryCountdownSeconds: Int? = null,
    val progressLog: List<WpsPixieLiveLogEntry> = emptyList(),
)

data class WpsPixieUiState(
    val sourceName: String? = null,
    val sourceKind: String? = null,
    val status: String = "Select a WPS PCAP or import a firmware serial log.",
    val validating: Boolean = false,
    val captured: com.swp81x.nrsuite.core.wps.WpsPixieCapturedMaterial? = null,
    val authKeyHex: String = "",
    val running: Boolean = false,
    val progressText: String = "",
    val result: com.swp81x.nrsuite.core.wps.WpsPixieResult? = null,
    val error: String? = null,
)
