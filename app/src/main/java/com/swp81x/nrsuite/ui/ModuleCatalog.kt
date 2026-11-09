package com.swp81x.nrsuite.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.FilterCenterFocus
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Nfc
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.Radio
import androidx.compose.material.icons.filled.Router
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SettingsInputAntenna
import androidx.compose.material.icons.filled.SettingsRemote
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiFind
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.swp81x.nrsuite.ui.components.ModuleCardSpec

internal val WIFI_RECON_MODULE_IDS = listOf("wifi", "sniff", "probe_monitor", "channel_analyzer", "hidden_ap")
internal val WIFI_ATTACK_MODULE_IDS =
    listOf("wps_pixie_live", "evil_twin", "portal", "nrugochi", "deauth", "beacon")
internal val WIFI_DEFEND_MODULE_IDS =
    listOf("deauth_detector", "rogue_ap", "client_presence", "wps_lock_detector", "wps_attempt_detector")
internal val WIFI_ALL_MODULE_IDS =
    WIFI_RECON_MODULE_IDS + WIFI_ATTACK_MODULE_IDS + WIFI_DEFEND_MODULE_IDS
internal val IR_CATEGORY_MODULE_IDS =
    listOf("ir", "ir_receiver", "ir_search", "ir_jammer")

internal data class CategorySpec(
    val name: String,
    val icon: ImageVector,
    val iconTint: Color,
    val moduleIds: List<String>,
    val available: Boolean,
    val statusLabel: String? = null,
)

internal val modules = listOf(

    ModuleCardSpec(
        id = "wifi",
        title = "WiFi Scan",
        description = "Active band-aware scan with SSID, BSSID, channel, RSSI, and security.",
        icon = Icons.Default.Wifi,
        category = "WiFi Recon",
        radio = "2.4 / 5 GHz",
        available = true,
    ),

    ModuleCardSpec(
        id = "sniff",
        title = "Packet Sniffer",
        description = "Capture 802.11 frames and export PCAP with band-aware configuration.",
        icon = Icons.Default.FilterCenterFocus,
        category = "WiFi Recon",
        radio = "2.4 / 5 GHz",
        available = true,
    ),

    ModuleCardSpec(
        id = "hidden_ap",
        title = "Hidden AP Revealer",
        description = "Reveal hidden SSIDs from beacon and client traffic.",
        icon = Icons.Default.VisibilityOff,
        category = "WiFi Recon",
        radio = "2.4GHz",
        available = true,
    ),

    ModuleCardSpec(
        id = "probe_monitor",
        title = "Probe Request Monitor",
        description = "Aggregate nearby client probe requests by MAC, SSID, channel, and RSSI.",
        icon = Icons.Default.Radar,
        category = "WiFi Recon",
        radio = "2.4GHz",
        available = true,
    ),

    ModuleCardSpec(
        id = "channel_analyzer",
        title = "Channel Analyzer",
        description = "Compare AP density, signal, security, and WPS usage across channels.",
        icon = Icons.Default.BarChart,
        category = "WiFi Recon",
        radio = "2.4 / 5 GHz",
        available = true,
    ),

    ModuleCardSpec(
        id = "beacon",
        title = "Beacon",
        description = "Broadcast custom or hidden SSIDs.",
        icon = Icons.Default.SettingsInputAntenna,
        category = "WiFi Attacks",
        radio = "2.4GHz",
        available = true,
    ),

    ModuleCardSpec(
        id = "deauth",
        title = "Deauth",
        description = "Send a targeted deauth burst to a BSSID.",
        icon = Icons.Default.WifiOff,
        category = "WiFi Attacks",
        radio = "2.4GHz",
        available = true,
    ),

    ModuleCardSpec(
        id = "portal",
        title = "Captive Portal",
        description = "Start an AP and serve a custom HTML page.",
        icon = Icons.Default.Language,
        category = "WiFi Attacks",
        radio = "2.4GHz",
        available = true,
    ),

    ModuleCardSpec(
        id = "evil_twin",
        title = "Evil Twin",
        description = "Portal + deauth + EAPOL capture workflow.",
        icon = Icons.Default.ContentCopy,
        category = "WiFi Attacks",
        radio = "2.4GHz",
        available = true,
    ),

    ModuleCardSpec(
        id = "nrugochi",
        title = "NRuGochi",
        description = "Pwnagotchi-style channel hunting and automatic WPA2 handshake capture.",
        icon = Icons.Default.Key,
        category = "WiFi Attacks",
        radio = "2.4GHz",
        available = true,
    ),

    ModuleCardSpec(
        id = "deauth_detector",
        title = "Deauth Detector",
        description = "Passively detect deauth and disassoc frames around you.",
        icon = Icons.Default.WifiFind,
        category = "WiFi Defense",
        radio = "2.4GHz",
        available = true,
    ),

    ModuleCardSpec(
        id = "rogue_ap",
        title = "Rogue AP Detector",
        description = "Compare visible APs against a trusted baseline and OUI rules.",
        icon = Icons.Default.Router,
        category = "WiFi Defense",
        radio = "2.4GHz",
        available = true,
    ),

    ModuleCardSpec(
        id = "client_presence",
        title = "Client Detector",
        description = "Passively detect client management frames, with optional active reconnection triggering.",
        icon = Icons.Default.PersonSearch,
        category = "WiFi Defense",
        radio = "2.4GHz",
        available = true,
    ),

    ModuleCardSpec(
        id = "wps_lock_detector",
        title = "WPS Lock Detector",
        description = "Passively discover WPS access points and report locked, likely unlocked, or unknown lock state.",
        icon = Icons.Default.Lock,
        category = "WiFi Defense",
        radio = "2.4 / 5 GHz",
        available = true,
    ),

    ModuleCardSpec(
        id = "wps_attempt_detector",
        title = "WPS Attempt Detector",
        description = "Passively observe WPS Identity/M1–M8 activity with source-MAC vendor and risk context.",
        icon = Icons.Default.WifiFind,
        category = "WiFi Defense",
        radio = "2.4 / 5 GHz",
        available = true,
    ),

    ModuleCardSpec(
        id = "ble_scanner",
        title = "BLE Scanner",
        description = "Discover nearby BLE devices, services, and manufacturer data.",
        icon = Icons.Default.Bluetooth,
        category = "BLE",
        radio = "BLE",
        available = true,
    ),

    ModuleCardSpec(
        id = "ble_profile",
        title = "BLE GATT Profile",
        description = "Read-only service and characteristic enumeration for a BLE device.",
        icon = Icons.Default.DeveloperBoard,
        category = "BLE",
        radio = "BLE",
        available = true,
    ),

    ModuleCardSpec(
        id = "tracker_detector",
        title = "Tracker Detector",
        description = "Detect Find My / AirTag-style BLE tracker advertisements.",
        icon = Icons.Default.Radar,
        category = "BLE",
        radio = "BLE",
        available = true,
    ),

    ModuleCardSpec(
        id = "ld2450",
        title = "mmWave Radar",
        description = "Track up to 3 targets with X/Y, distance, and speed.",
        icon = Icons.Default.Radar,
        category = "Sensors",
        radio = "24GHz",
        available = true,
    ),

    ModuleCardSpec(
        id = "mesh",
        title = "Mesh",
        description = "Provision, monitor, and control the ESP-NOW mesh.",
        icon = Icons.Default.SettingsInputAntenna,
        category = "Mesh",
        radio = "2.4GHz",
        available = true,
    ),

    ModuleCardSpec(
        id = "mac_lookup",
        title = "MAC Lookup",
        description = "Offline OUI/vendor lookup for a MAC or BSSID. No map or network request.",
        icon = Icons.Default.Search,
        category = "Tools",
        radio = "Offline",
        available = true,
    ),

    ModuleCardSpec(
        id = "bssid_geolocation",
        title = "BSSID Geolocation",
        description = "Offline vendor lookup plus approximate Wi-Fi access-point location.",
        icon = Icons.Default.LocationOn,
        category = "Tools",
        radio = "Wi-Fi",
        available = true,
    ),

    ModuleCardSpec(
        id = "credential_manager",
        title = "Credential Manager",
        description = "Review Evil Twin captures, offline crack results, and saved credentials.",
        icon = Icons.Default.Lock,
        category = "Credentials",
        available = true,
    ),

    ModuleCardSpec(
        id = "wpa_cracker",
        title = "WPA/WPA2 Cracker",
        description = "Offline handshake verification against a wordlist.",
        icon = Icons.Default.Key,
        category = "Credentials",
        radio = "2.4GHz",
        available = true,
    ),
    ModuleCardSpec(
        id = "pcap22000",
        title = "PCAP to .hs22000",
        description = "Merge captures and export Hashcat mode 22000 handshakes.",
        icon = Icons.Default.Folder,
        category = "Credentials",
        available = true,
    ),
    ModuleCardSpec(
        id = "wps_pixie_live",
        title = "WPS Pixie Dust",
        description = "Try a likely PIN, crack if needed, and recover the WPS PSK.",
        icon = Icons.Default.Wifi,
        category = "WiFi Attacks",
        radio = "2.4GHz",
        available = true,
    ),

    ModuleCardSpec(
        id = "ducky",
        title = "Ducky Editor",
        description = "Create, import, and export DuckyScript payloads.",
        icon = Icons.Default.Code,
        category = "HID",
        radio = "USB",
        available = true,
    ),

    ModuleCardSpec(
        id = "badusb",
        title = "BadUSB",
        description = "Native USB HID payloads.",
        icon = Icons.Default.Keyboard,
        category = "HID",
        radio = "USB",
        available = true,
    ),

    ModuleCardSpec(
        id = "pin_bruteforce",
        title = "PIN Bruteforce",
        description = "Supervised HID PIN range and wordlist testing.",
        icon = Icons.Default.Lock,
        category = "HID",
        radio = "USB",
        available = true,
    ),

    // Hidden/experimental: retained for future unlocked-host or mesh HID work.
    // Android/iOS lock screens generally ignore BLE HID input, so this module is
    // intentionally not exposed in the normal module grid.
    ModuleCardSpec(
        id = "ble_pin_bruteforce",
        title = "BLE HID PIN Bruteforce",
        description = "Experimental BLE HID PIN range and wordlist testing with live controls.",
        icon = Icons.Default.Bluetooth,
        category = "HID",
        radio = "BLE",
        available = false,
        statusLabel = "Experimental / hidden",
    ),

    ModuleCardSpec(
        id = "ble",
        title = "BLE HID",
        description = "BadBLE payloads and realtime keyboard input.",
        icon = Icons.Default.Bluetooth,
        category = "HID",
        radio = "BLE",
        available = true,
    ),

    ModuleCardSpec(
        id = "storage",
        title = "Mass Storage",
        description = "Browse and manage files on the device.",
        icon = Icons.Default.Folder,
        category = "Storage",
        radio = "USB",
        available = true,
    ),

    ModuleCardSpec(
        id = "firmware",
        title = "Firmware Flasher",
        description = "Flash a complete merged firmware image over the USB ROM bootloader.",
        icon = Icons.Default.Memory,
        category = "Firmware",
        radio = "USB",
        available = true,
    ),

    ModuleCardSpec(
        id = "serial_monitor",
        title = "Serial Monitor",
        description = "Raw USB serial monitor for any ESP32 or USB-UART firmware.",
        icon = Icons.Default.DeveloperBoard,
        category = "Firmware",
        radio = "USB",
        available = true,
    ),

    ModuleCardSpec(
        id = "ir",
        title = "IR Remotes",
        description = "Browse IR remotes, import Flipper .ir files, and transmit through phone or ESP32 IR.",
        icon = Icons.Default.SettingsRemote,
        category = "IR",
        radio = "IR",
        available = true,
    ),

    ModuleCardSpec(
        id = "ir_receiver",
        title = "IR Receiver",
        description = "Capture raw IR signals from the ESP32 receiver and save them as Flipper .ir files.",
        icon = Icons.Default.SettingsRemote,
        category = "IR",
        radio = "IR",
        available = true,
    ),

    ModuleCardSpec(
        id = "ir_search",
        title = "IR Finder",
        description = "Find a working IR button by stepping through matching category, brand, and button candidates.",
        icon = Icons.Default.Search,
        category = "IR",
        radio = "IR",
        available = true,
    ),

    ModuleCardSpec(
        id = "ir_jammer",
        title = "IR Jammer",
        description = "Time-limited IR interference test tool. Explicit confirmation and automatic stop required.",
        icon = Icons.Default.Stop,
        category = "IR",
        radio = "IR",
        available = true,
    ),

    ModuleCardSpec(
        id = "ir_config",
        title = "IR Configuration",
        description = "Choose the IR backend, manage the IR database, and configure ESP32 RX/TX pins.",
        icon = Icons.Default.Settings,
        category = "IR",
        radio = "IR",
        available = true,
    ),

    ModuleCardSpec(
        id = "rf",
        title = "RF",
        description = "Sub-GHz radio frequency module (planned).",
        icon = Icons.Default.Radio,
        category = "RF",
        radio = "Sub-1GHz",
        available = false,
        statusLabel = "Unavailable",
    ),

    ModuleCardSpec(
        id = "rfid",
        title = "RFID",
        description = "Temporarily unavailable while PN532 hardware support is paused.",
        icon = Icons.Default.Nfc,
        category = "RFID",
        radio = "13.56MHz",
        available = false,
        statusLabel = "Unavailable",
    ),
)
