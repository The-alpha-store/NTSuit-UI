package com.swp81x.nrsuite.core.wpa

data class Pcap22000Candidate(
    val sourceName: String,
    val detectedSsid: String? = null,
    val ssidOverride: String? = null,
    val effectiveSsid: String? = detectedSsid,
    val bssidHex: String? = null,
    val stationHex: String? = null,
)

data class Pcap22000Export(
    val lines: List<String> = emptyList(),
    val exportedCandidates: List<Pcap22000Candidate> = emptyList(),
    val duplicatesRemoved: Int = 0,
    val missingSsid: Int = 0,
    val unsupported: Int = 0,
)
