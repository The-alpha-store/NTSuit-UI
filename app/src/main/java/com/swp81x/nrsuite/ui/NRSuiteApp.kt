package com.swp81x.nrsuite.ui

import android.content.Context
import android.hardware.usb.UsbManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.swp81x.nrsuite.core.session.ConnectionState
import com.swp81x.nrsuite.core.wifi.WifiBand

/**
 * UI-only preview build.
 *
 * Every screen below is the ORIGINAL design from the full NRSuite app,
 * unchanged pixel-for-pixel. What is gone is everything that made it work:
 * there is no WiFi/BLE/USB/RFID hardware access, no packet capture or
 * injection, no cracking, no credential capture, and no network calls.
 * Every screen is fed static placeholder state and every action button is a
 * no-op, so this build is a navigable mockup of the interface only.
 */
@Composable
fun NRSuiteApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val usbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val tabs = listOf(
        AppTab("home", "Home", Icons.Default.Home),
        AppTab("modules", "Modules", Icons.Default.Widgets),
        AppTab("logs", "Logs", Icons.Default.History),
        AppTab("more", "More", Icons.Default.MoreHoriz),
        AppTab("settings", "Settings", Icons.Default.Settings),
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEach { tab ->
                    NavigationBarItem(
                        selected = currentRoute == tab.route,
                        onClick = {
                            navController.navigate(tab.route) {
                                popUpTo("home") { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(tab.icon, contentDescription = tab.label) },
                        label = { Text(tab.label) },
                    )
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            composable("home") {
            HomeScreen(
                modules = modules,
                listState = rememberLazyListState(),
                onOpenModule = { id -> navController.navigate("module/$id") },
                onOpenCategory = { cat -> navController.navigate("category/$cat") },
                onConnect = { },
                onDisconnect = { },
                onManageDevice = { navController.navigate("device") },
                connectionState = ConnectionState.Disconnected,
                permissionRevision = 0,
                devices = emptyList(),
                deviceConnectionStates = emptyMap(),
                deviceFingerprint = { },
                pendingPermissionRequests = emptySet(),
                disconnectingFingerprints = emptySet(),
                usbManager = usbManager,
                recentModuleIds = emptyList(),
                ouiDatabaseStatus = OuiDatabaseStatus.Checking,
                ouiDatabaseProgress = null,
                appUpdate = null,
                appUpdateDownload = AppUpdateDownloadState.Idle,
                firmwareUpdate = null,
                firmwareAssetDownload = FirmwareAssetDownloadState.Idle,
                onDownloadOuiDatabase = { },
                onDownloadAppUpdate = { },
                onInstallAppUpdate = { },
                onDownloadFirmwareUpdate = { },
                onOpenFirmwareFlasher = { },
                onRefreshDevices = { },
            )
            }
            composable("modules") {
            ModulesScreen(
                modules = modules,
                onOpenModule = { id -> navController.navigate("module/$id") },
            )
            }
            composable("category/{categoryName}") { backStackEntry ->
                val categoryName = backStackEntry.arguments?.getString("categoryName").orEmpty()
                CategoryModulesScreen(
                    modules = modules.filter { it.category == categoryName },
                    onOpenModule = { id -> navController.navigate("module/$id") },
                )
            }
            composable("logs") {
            LogsScreen(
                logs = emptyList(),
                history = emptyList(),
                onClearLogs = { },
                onClearHistory = { },
            )
            }
            composable("settings") {
            SettingsScreen(
                connectionState = ConnectionState.Disconnected,
                exportDirectoryName = "",
                firmwareFileName = null,
                firmwareFlashSize = 0L,
                firmwareFlashing = false,
                firmwareFlashProgress = 0,
                firmwareFlashStatus = null,
                devices = emptyList(),
                usbManager = usbManager,
                permissionRevision = 0,
                selectedFlashTarget = null,
                onChooseExportDirectory = { },
                onChooseFirmware = { },
                onClearFirmware = { },
                onRefreshDevices = { },
                onRequestPermission = { },
                onSelectFlashTarget = { },
                onStartFirmwareFlash = { },
            )
            }
            composable("device") {
            DeviceScreen(
                usbManager = usbManager,
                connectionState = ConnectionState.Disconnected,
                devices = emptyList(),
                deviceConnectionStates = emptyMap(),
                deviceFingerprint = { },
                pendingPermissionRequests = emptySet(),
                disconnectingFingerprints = emptySet(),
                onRefresh = { },
                onConnect = { },
                onDisconnect = { },
                onDisconnectDevice = { },
            )
            }
            composable("more") {
                MoreScreensList(navController = navController)
            }
        composable("module/wifi") {
        WifiScanScreen(
            connected = false,
            scanning = false,
            networks = emptyList(),
            band = WifiBand.AUTO,
            showBandSelector = false,
            fiveGhzAvailable = false,
            fiveGhzUnavailableMessage = "",
            onBandSelected = { },
            onScan = { },
        )
        }
        composable("module/sniff") {
        SniffScreen(
            connected = false,
            sniffing = false,
            packetCount = 0L,
            captureBytes = 0L,
            capturePath = null,
            handshake = EapolHandshake(),
            scanning = false,
            networks = emptyList(),
            onScanWifi = { },
            onStart = { },
            onStop = { },
        )
        }
        composable("module/hidden_ap") {
        HiddenApScreen(
            connected = false,
            running = false,
            starting = false,
            fixed = false,
            channel = 0,
            currentHopChannel = null,
            observations = emptyList(),
            candidates = emptyList(),
            deauthEnabled = false,
            onFixedChange = { },
            onChannelChange = { },
            onDeauthToggle = { },
            onStart = { },
            onStop = { },
            onForceReconnect = { },
            onClearCandidates = { },
            onClearObservations = { },
        )
        }
        composable("module/probe_monitor") {
        ProbeMonitorScreen(
            connected = false,
            running = false,
            fixed = false,
            channel = 0,
            entries = emptyList(),
            frameCount = 0L,
            allowedChannels = emptyList(),
            onStart = { },
            onStop = { },
        )
        }
        composable("module/channel_analyzer") {
        ChannelAnalyzerScreen(
            connected = false,
            scanning = false,
            networks = emptyList(),
            onScan = { },
        )
        }
        composable("module/beacon") {
        BeaconScreen(
            connected = false,
            running = false,
            sentFrames = 0,
            ssidCount = 0,
            activeChannel = 0,
            savedLists = emptyMap(),
            onSaveListAction = { },
            onRenameListAction = { },
            onDeleteList = { },
            onStart = { },
            onStop = { },
        )
        }
        composable("module/deauth") {
        DeauthScreen(
            connected = false,
            running = false,
            sentFrames = 0,
            failedFrames = 0,
            elapsedMs = 0L,
            pps = 0.0,
            targetBssid = "",
            activeChannel = 0,
            scanning = false,
            networks = emptyList(),
            onScanWifi = { },
            onStart = { },
            onStop = { },
        )
        }
        composable("module/portal") {
        PortalScreen(
            connected = false,
            running = false,
            htmlSize = 0,
            htmlComplete = false,
            activeSsid = "",
            activeChannel = 0,
            portalViews = 0,
            portalClients = 0,
            capturedData = 0,
            credentials = emptyList(),
            selectedHtmlName = null,
            selectedHtmlSize = null,
            htmlUploading = false,
            htmlProgress = 0,
            eventLog = emptyList(),
            onChooseHtml = { },
            onClearEventLog = { },
            onStart = { },
            onStop = { },
        )
        }
        composable("module/evil_twin") {
        EvilTwinScreen(
            connected = false,
            running = false,
            scanning = false,
            networks = emptyList(),
            handshake = EapolHandshake(),
            results = emptyList(),
            eventLog = emptyList(),
            selectedHtmlName = null,
            selectedHtmlSize = null,
            htmlUploading = false,
            htmlProgress = 0,
            htmlComplete = false,
            onClearEventLog = { },
            onScanWifi = { },
            onChooseHtml = { },
            onStart = { },
            onStop = { },
            onClearPasswords = { },
        )
        }
        composable("module/nrugochi") {
        NrugochiScreen(
            connected = false,
            state = AutoHandshakeUiState(),
            sessions = emptyList(),
            feed = emptyList(),
            pwnMode = PwnModeState(),
            captures = emptyList(),
            onStart = { },
            onStop = { },
            onResumeSession = { },
            onDeleteSession = { },
            onClearSessions = { },
            onPwnModeEnabledChange = { },
            onAddPwnPassword = { },
            onRemovePwnPassword = { },
            onCrackCapture = { },
            onDeleteCapture = { },
            onResetStats = { },
        )
        }
        composable("module/deauth_detector") {
        DeauthDetectorScreen(
            connected = false,
            running = false,
            channelMode = DeauthChannelMode.TARGETED,
            onChannelModeChange = { },
            hopIntervalMs = 0,
            onHopIntervalChange = { },
            framesPerSecond = 0,
            totalFrames = 0,
            uniqueSourceCount = 0,
            thresholdFramesPerSecond = 0,
            activeAlert = null,
            feed = emptyList(),
            feedFilter = DeauthFeedFilter.ALL,
            scanResults = emptyList(),
            selectedTarget = null,
            channel = 0,
            onSelectTarget = { },
            onScanClick = { },
            isScanning = false,
            onFeedFilterChange = { },
            onClearFeed = { },
            onStart = { },
            onStop = { },
        )
        }
        composable("module/rogue_ap") {
        RogueApScreen(
            connected = false,
            running = false,
            scanning = false,
            nearbyNetworks = emptyList(),
            alerts = emptyList(),
            lastScanAt = null,
            ouiDatabaseStatus = OuiDatabaseStatus.Checking,
            onDownloadOuiDatabase = { },
            onStart = { },
            onStop = { },
            onClearAlerts = { },
        )
        }
        composable("module/client_presence") {
        ClientPresenceScreen(
            connected = false,
            running = false,
            mode = ClientPresenceMode.PASSIVE,
            fixed = false,
            channel = 0,
            targetBssid = "",
            scanning = false,
            networks = emptyList(),
            clients = emptyList(),
            frameCount = 0L,
            lastTriggerAt = null,
            onModeChange = { },
            onFixedChange = { },
            onChannelChange = { },
            onScanWifi = { },
            onSelectTarget = { },
            onStart = { },
            onStop = { },
            onTriggerReconnect = { },
            onClear = { },
        )
        }
        composable("module/wps_lock_detector") {
        WpsLockDetectorScreen(
            connected = false,
            scanning = false,
            accessPoints = emptyList(),
            error = null,
            onScan = { },
        )
        }
        composable("module/wps_attempt_detector") {
        WpsAttemptDetectorScreen(
            connected = false,
            state = WpsAttemptDetectorUiState(),
            onScanWpsTargets = { },
            onStart = { },
            onStop = { },
        )
        }
        composable("module/ble_scanner") {
        BleScannerScreen(
            connected = false,
            running = false,
            starting = false,
            stopping = false,
            active = false,
            devices = emptyList(),
            onActiveChange = { },
            onStart = { },
            onStop = { },
            onClear = { },
        )
        }
        composable("module/ble_profile") {
        BleProfileScreen(
            connected = false,
            running = false,
            scanStarting = false,
            scanRunning = false,
            scanStopping = false,
            target = null,
            devices = emptyList(),
            services = emptyList(),
            status = "",
            onSelectTarget = { },
            onStartScan = { },
            onStopScan = { },
            onStart = { },
            onStop = { },
            onClear = { },
        )
        }
        composable("module/tracker_detector") {
        TrackerDetectorScreen(
            connected = false,
            scanning = false,
            trackers = emptyList(),
            onStart = { },
            onStop = { },
            onClear = { },
        )
        }
        composable("module/ld2450") {
        Ld2450Screen(
            connected = false,
            state = Ld2450UiState(),
            onStart = { },
            onStop = { },
            onRefresh = { },
            onMaxRangeChange = { },
            onZoneAlertChange = { },
            onMeshBroadcastChange = { },
            onHeartbeatEnabledChange = { },
            onScanDurationChange = { },
            onSleepDurationChange = { },
            onLimitDotCountChange = { },
            onMaxDotsChange = { },
        )
        }
        composable("module/mesh") {
        MeshScreen(
            connected = false,
            initialized = false,
            role = "",
            active = false,
            sessionId = null,
            nodeId = "",
            channel = 0,
            passphrase = "",
            keyId = "",
            passphraseMatch = null,
            checkingPassphrase = false,
            provisioning = false,
            actionInProgress = false,
            setupMessage = null,
            lastError = null,
            hasStoredCredentials = false,
            storedKeyId = null,
            hasGlobalCredentials = false,
            globalKeyId = null,
            nodes = emptyList(),
            onRefresh = { },
            onPassphraseChange = { },
            onKeyIdChange = { },
            onChannelChange = { },
            onCheckPassphrase = { },
            onProvision = { },
            onProvisionWithSavedCredentials = { },
            onAuthenticateAndActivate = { },
            onAuthenticateStored = { },
            onDeactivate = { },
            onApplyChannel = { },
            onForgetPassphrase = { },
            onClearKeys = { },
            onClearNodes = { },
            onClearSetupMessage = { },
            onClearError = { },
        )
        }
        composable("module/mac_lookup") {
        MacLookupScreen(
            result = null,
            geolocationState = BssidGeolocationState.Idle,
            nearbyState = BssidNearbyState.Idle,
            history = emptyList(),
            onLookup = { },
            onSelectHistory = { },
            onDeleteHistory = { },
            onClearHistory = { },
            onClear = { },
            onNearbyToggle = { },
        )
        }
        composable("module/bssid_geolocation") {
        MacLookupScreen(
            result = null,
            geolocationState = BssidGeolocationState.Idle,
            nearbyState = BssidNearbyState.Idle,
            history = emptyList(),
            onLookup = { },
            onSelectHistory = { },
            onDeleteHistory = { },
            onClearHistory = { },
            onClear = { },
            onNearbyToggle = { },
        )
        }
        composable("module/credential_manager") {
        CredentialManagerScreen(
            sessions = emptyList(),
            onDeleteSession = { },
            onClearAll = { },
        )
        }
        composable("module/wpa_cracker") {
        WpaCrackerScreen(
            sessions = emptyList(),
            capturedHandshakes = emptyList(),
            selectedSession = null,
            selectedCapturedPath = null,
            sourceType = CrackerSourceType.SAVED_CAPTURE,
            customPcapName = null,
            imported22000Name = null,
            targets = emptyList(),
            targetsLoading = false,
            targetMessage = null,
            manualMode = false,
            manualSsid = "",
            wordlistName = null,
            running = false,
            paused = false,
            tested = 0L,
            speed = 0.0,
            result = null,
            status = "",
            currentTargetIndex = 0,
            selectedTargetCount = 0,
            currentTargetSsid = "",
            progressFraction = 0f,
            progressKnown = false,
            savedSessions = emptyList(),
            onSourceSelected = { },
            onSelectSession = { },
            onSelectCapturedHandshake = { },
            onChooseCustomPcap = { },
            onChoose22000 = { },
            onClearSource = { },
            onManualModeChange = { },
            onManualSsidChange = { },
            onToggleTarget = { },
            onSelectAllTargets = { },
            onChooseWordlist = { },
            onClearWordlist = { },
            onStart = { },
            onPause = { },
            onResume = { },
            onStop = { },
            onResumeSession = { },
            onDeleteSession = { },
        )
        }
        composable("module/pcap22000") {
        Pcap22000Screen(
            state = Pcap22000UiState(),
            onAddPcaps = { },
            onRemoveInput = { },
            onClearInputs = { },
            onSetSsidOverride = { },
            onRemoveSsidOverride = { },
            onExport = { },
        )
        }
        composable("module/wps_pixie_live") {
        WpsPixieLiveScreen(
            connected = false,
            state = WpsPixieLiveUiState(),
            onScan = { },
            onWpsOnlyChange = { },
            onSelectTarget = { },
            onCandidatePinChange = { },
            onUseGeneratedPin = { },
            onStart = { },
            onStop = { },
            onClearLogs = { },
        )
        }
        composable("module/ducky") {
        DuckyEditorScreen(
            savedScripts = emptyMap(),
            onSaveScript = { },
            onDeleteScript = { },
        )
        }
        composable("module/badusb") {
        BadUsbScreen(
            devices = emptyList(),
            selectedTarget = null,
            selectedTargetState = null,
            targetFingerprint = { },
            deviceConnectionStates = emptyMap(),
            pendingPermissionRequests = emptySet(),
            uploading = false,
            progress = 0,
            armedFingerprints = emptySet(),
            selectedPayloadName = null,
            savedScripts = emptyMap(),
            onSelectTarget = { },
            onChooseTarget = { },
            onUseSavedScript = { },
            onChoosePayload = { },
            onClearPayload = { },
            onArm = { },
        )
        }
        composable("module/pin_bruteforce") {
        PinBruteforceScreen(
            connected = false,
            state = PinBruteforceUiState(),
            attempts = emptyList(),
            onSourceChange = { },
            onPinLengthChange = { },
            onStartChange = { },
            onEndChange = { },
            onTimeoutChange = { },
            onMaxAttemptsChange = { },
            onPersistEveryChange = { },
            onAttemptsBeforeTimeoutChange = { },
            onCooldownChange = { },
            onInterAttemptChange = { },
            onTypingDelayChange = { },
            onStartDelayChange = { },
            onAutoResumeChange = { },
            onSessionKeyChange = { },
            onWordlistSelected = { },
            onClearWordlist = { },
            onStart = { },
            onStop = { },
            onRefresh = { },
            onClearSession = { },
        )
        }
        composable("module/ble_pin_bruteforce") {
        BlePinBruteforceScreen(
            connected = false,
            state = BlePinBruteforceUiState(),
            attempts = emptyList(),
            onSourceChange = { },
            onPinLengthChange = { },
            onStartChange = { },
            onEndChange = { },
            onTimeoutChange = { },
            onMaxAttemptsChange = { },
            onPersistEveryChange = { },
            onAttemptsBeforeTimeoutChange = { },
            onCooldownChange = { },
            onInterAttemptChange = { },
            onTypingDelayChange = { },
            onStartDelayChange = { },
            onAutoResumeChange = { },
            onAutoStartOnConnectChange = { },
            onPauseOnDisconnectChange = { },
            onAutoReconnectChange = { },
            onSessionKeyChange = { },
            onWordlistSelected = { },
            onClearWordlist = { },
            onStartAdvertising = { },
            onStopAdvertising = { },
            onStart = { },
            onPause = { },
            onResume = { },
            onStop = { },
            onNext = { },
            onPrevious = { },
            onClearSession = { },
            onForgetBond = { },
            onDisconnect = { },
            onRefresh = { },
        )
        }
        composable("module/ble") {
        BleScreen(
            connected = false,
            advertising = false,
            bleConnected = false,
            peer = "",
            selectedPayloadName = null,
            savedScripts = emptyMap(),
            modifiers = emptySet(),
            modifierHold = false,
            bleScriptRunning = false,
            onUseSavedScript = { },
            onChoosePayload = { },
            onClearPayload = { },
            onStartAdvertising = { },
            onStop = { },
            onRunPayload = { },
            onRealtimeInput = { },
            onSpecialKey = { },
            onModifierChange = { },
            onModifierHoldChange = { },
            onMouseMove = { },
            onMouseScroll = { },
            onMouseButton = { },
        )
        }
        composable("module/storage") {
        StorageScreen(
            connected = false,
            loading = false,
            files = emptyList(),
            totalBytes = 0L,
            usedBytes = 0L,
            freeBytes = 0L,
            onRefresh = { },
            onDelete = { },
            onStartMassStorage = { },
        )
        }
        composable("module/firmware") {
        DeviceScreen(
            connectionState = ConnectionState.Disconnected,
            devices = emptyList(),
            deviceConnectionStates = emptyMap(),
            deviceFingerprint = { },
            pendingPermissionRequests = emptySet(),
            disconnectingFingerprints = emptySet(),
            usbManager = usbManager,
            onRefresh = { },
            onConnect = { },
            onDisconnect = { },
            onDisconnectDevice = { },
        )
        }
        composable("module/serial_monitor") {
        SerialMonitorScreen(
            devices = emptyList(),
            selectedDevice = null,
            connected = false,
            connecting = false,
            baudRate = 0,
            logLines = emptyList(),
            hexMode = false,
            inputText = "",
            lineEnding = "",
            blockingMessage = null,
            errorMessage = null,
            permissionRevision = 0,
            hasPermission = { },
            onRefreshDevices = { },
            onSelectDevice = { },
            onRequestPermission = { },
            onConnect = { },
            onDisconnect = { },
            onBaudChange = { },
            onHexModeChange = { },
            onInputChange = { },
            onLineEndingChange = { },
            onSend = { },
            onClearLog = { },
            onExport = { },
        )
        }
        composable("module/ir") {
        IrScreen(
            remotes = emptyList(),
            selectedRemote = null,
            loadingRemote = false,
            importing = false,
            transmitting = false,
            capabilities = IrPhoneCapabilities(),
            esp32IrReady = false,
            txAvailable = false,
            backend = IrTransmitBackend.AUTO,
            loopMode = false,
            activeLoopSignalId = null,
            loopIntervalMs = 0,
            scanProgress = null,
            errorMessage = null,
            onImport = { },
            onRefreshLibrary = { },
            onOpenRemote = { },
            openedFromFinder = false,
            onToggleFavorite = { },
            onDeleteRemote = { },
            onSendSignal = { },
            onToggleSignal = { },
            onSetLoopMode = { },
            onStopTransmission = { },
            onClearMessages = { },
        )
        }
        composable("module/ir_receiver") {
        IrReceiverScreen(
            esp32 = null,
            pendingCapture = null,
            learnedButtons = emptyList(),
            nextButtonNumber = 0,
            suggestedRemoteFileName = "",
            savingRemote = false,
            txAvailable = false,
            errorMessage = null,
            onRefresh = { },
            onStartReceiver = { },
            onStopReceiver = { },
            onSavePending = { },
            onDiscardPending = { },
            onReplayPending = { },
            onDeleteLearned = { },
            onRenameLearned = { },
            onReplayLearned = { },
            onClearLearned = { },
            onSaveRemote = { },
        )
        }
        composable("module/ir_search") {
        IrSearchScreen(
            remotes = emptyList(),
            state = IrSearchUiState(),
            commonButtonsState = IrCommonButtonsState(),
            initialCategory = "",
            initialBrand = "",
            onSelectionChanged = { },
            onPrepareCommonButtons = { },
            onRefreshCommonButtons = { },
            onStart = { },
            onPause = { },
            onResume = { },
            onPrevious = { },
            onNext = { },
            onManualSend = { },
            onToggleLoop = { },
            onStop = { },
            onFound = { },
            onOpenMarkedRemote = { },
            onRemoveMarkedResult = { },
        )
        }
        composable("module/ir_jammer") {
        IrJammerScreen(
            state = IrJammerUiState(),
            txAvailable = false,
            onStart = { },
            onStop = { },
        )
        }
        composable("module/ir_config") {
        IrConfigScreen(
            capabilities = IrPhoneCapabilities(),
            backend = IrTransmitBackend.AUTO,
            loopIntervalMs = 0,
            exportDirectoryName = "",
            remoteCount = 0,
            importing = false,
            scanProgress = null,
            irDatabaseState = IrDatabaseUiState(),
            irDatabaseMetadata = null,
            esp32 = null,
            errorMessage = null,
            onSetBackend = { },
            onSetLoopInterval = { },
            onRefreshCapabilities = { },
            onRescanLibrary = { },
            onDownloadIrDatabase = { },
            onRetryIrDatabaseOperation = { },
            onCancelIrDatabaseOperation = { },
            onImportIrZip = { },
            onDeleteIrDatabase = { },
            onRefreshEsp32Ir = { },
            onSetIrEsp32Pins = { },
        )
        }
        composable("module/rfid") {
        RfidScreen(
            connected = false,
            state = RfidUiState(),
            onStart = { },
            onStop = { },
            onRefresh = { },
            onClearHistory = { },
            onLeave = { },
        )
        }
        composable("extra/wps_pixie") {
        WpsPixieScreen(
            state = WpsPixieUiState(),
            onChoosePcap = { },
            onChooseSerialLog = { },
            onClear = { },
            onAuthKeyChange = { },
            onStart = { },
            onStop = { },
        )
        }
        composable("extra/offline_mac_lookup") {
        OfflineMacLookupScreen(
            result = null,
            ouiDatabaseStatus = OuiDatabaseStatus.Checking,
            onLookup = { },
            onClear = { },
            onDownloadOuiDatabase = { },
        )
        }
        composable("extra/rfid_config") {
        RfidConfigScreen(
            connected = false,
            state = RfidUiState(),
            onRefresh = { },
            onSave = { },
        )
        }
        composable("extra/sensor_config") {
        SensorConfigScreen(
            connected = false,
            ld2450 = Ld2450UiState(),
            onRefresh = { },
            onSetLd2450Pins = { },
        )
        }
        composable("extra/wifi_config") {
        WifiConfigScreen(
            connected = null,
            band = WifiBand.AUTO,
            showBandSelector = false,
            fiveGhzAvailable = false,
            fiveGhzUnavailableMessage = "",
            onBandSelected = { },
            onCountrySelected = { },
            captureOutputLabel = "",
            hasCustomCaptureOutput = false,
            onChooseCaptureOutput = { },
            onResetCaptureOutput = { },
        )
        }
        composable("extra/ducky_editor") {
        DuckyEditorScreen(
            savedScripts = emptyMap(),
            onSaveScript = { },
            onDeleteScript = { },
        )
        }
        }
    }
}

private data class AppTab(
    val route: String,
    val label: String,
    val icon: ImageVector,
)

/**
 * The original app reached these screens through deeper in-screen
 * navigation (config gears, nested tabs, etc). They are collected here so
 * every screen in the project is still reachable in this flattened preview
 * nav.
 */
@Composable
private fun MoreScreensList(navController: NavController) {
    val items = listOf(
        Triple("wps_pixie", "WPS Pixie Dust (PCAP)", "WpsPixieScreen"),
        Triple("offline_mac_lookup", "Offline MAC Lookup", "OfflineMacLookupScreen"),
        Triple("rfid_config", "RFID Configuration", "RfidConfigScreen"),
        Triple("sensor_config", "Sensor Configuration", "SensorConfigScreen"),
        Triple("wifi_config", "WiFi Configuration", "WifiConfigScreen"),
        Triple("ducky_editor", "Ducky Script Editor", "DuckyEditorScreen"),
    )
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items) { (routeId, label, screenName) ->
            ListItem(
                headlineContent = { Text(label) },
                supportingContent = { Text(screenName) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("extra/$routeId") },
            )
            HorizontalDivider()
        }
    }
}
