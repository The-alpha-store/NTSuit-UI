# UI-Only Build — What Changed

This is a stripped-down fork of the original NRSuite app. Every screen's
visual design is untouched. Everything that made the app *do* anything has
been removed.

## Removed entirely
- `service/` (all 4 foreground services)
- `MainViewModel.kt` and its 24 extension files (~1,500 members of real logic)
- ~60 `core/` files implementing actual protocol/attack logic: WPA/WPS
  cracking, deauth injection, evil-twin credential capture, BLE PIN
  bruteforce, RFID, ESP32 flashing, mesh crypto, IR transmission, OUI
  database download, PCAP parsing, USB serial transport, etc.
- All hardware/network/notification permissions from the manifest
- `usb-serial-for-android` library dependency, NDK/native build config
  (no native source shipped with the project anyway)
- Docs and test fixtures describing the removed attack features

## Kept / rebuilt
- Every file under `ui/` is byte-for-byte the original.
- ~45 `core/` files that are pure data (no logic) — kept because screens
  need the types to compile. A handful of these were reconstructed from
  data classes that used to live inside deleted logic files (e.g.
  `EapolHandshake`, `HtmlTemplate`, `Pcap22000Candidate`, a few `*UiState`
  shapes) — same shape, no behavior.
- `ui/NRSuiteApp.kt` is **new**: it replaces the old 2,944-line
  ViewModel-driven navigation with a small nav shell (bottom tabs: Home /
  Modules / Logs / More / Settings) and a route per screen. Every screen is
  called with generated placeholder state (`false`, `0`, `emptyList()`,
  `ConnectionState.Disconnected`, etc.) and no-op callbacks.
- A few screens that reach secondary screens through in-screen navigation in
  the original app (config gears, nested tabs) are instead reachable from
  a new **More** tab, so nothing in the project is orphaned.

## Known caveats
- This was built without access to the Android SDK or a Gradle/Google
  Maven connection, so it has **not** been compiled or run. I did
  extensive static verification instead: every `core.*`/top-level import
  across the whole project was checked for a matching declaration, every
  screen's real signature was parsed directly from source (not guessed)
  to generate matching mock call sites, and brace/paren balance was
  checked file-by-file. Open it in Android Studio and let it sync — some
  small fixes may still be needed (an unused import, a default-value edge
  case), but the structure should be sound.
- `ui/components/BssidMapView.kt` (kept, untouched) renders a map via a
  local Leaflet HTML/JS asset bundle that wasn't present in the uploaded
  project, so that one screen will likely show a blank WebView until you
  add the asset files back (or swap it for a static placeholder).
- A couple of screens (`WifiReconExtraRoutes.kt`'s `ProbeMonitorRoute`/
  `ChannelAnalyzerRoute`) are now unused leftovers — harmless, but you can
  delete them if you want to tidy up further.
