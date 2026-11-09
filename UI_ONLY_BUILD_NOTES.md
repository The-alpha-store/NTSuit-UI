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
  Maven connection, so it was never compiled locally -- only statically
  verified (import resolution, signature parsing, brace/paren balance).
  A first real CI build caught three categories of real bugs, now fixed:
  1. `ui/NRSuiteApp.kt` and `ui/ModuleCatalog.kt` were missing several
     `import` statements for types they reference (e.g.
     `OuiDatabaseStatus`, `AppUpdateDownloadState`, `ModuleCardSpec`).
     Fixed by generating a complete import list from actual usage.
  2. `ui/ModuleCatalog.kt` was missing `CategorySpec` and five
     `*_MODULE_IDS` constants that `HomeScreen.kt` depends on -- these
     used to live next to the module list in the original `NRSuiteApp.kt`
     and got lost in the rewrite. Restored, same shape as the original.
  3. Mock lambda placeholders for multi-parameter or non-`Unit`-returning
     callbacks (e.g. `deviceFingerprint: (UsbDevice) -> String`) were
     generated as a blind `{ }`, which doesn't satisfy the real arity or
     return type. Regenerated arity- and return-type-aware (e.g.
     `{ _ -> "" }`, `{ _, _, _ -> }`).
  The GitHub Actions workflow is the real check -- push this and read its
  log; it's a much stronger signal than anything verified here.

- `ui/components/BssidMapView.kt` (kept, untouched) renders a map via a
  local Leaflet HTML/JS asset bundle that wasn't present in the uploaded
  project, so that one screen will likely show a blank WebView until you
  add the asset files back (or swap it for a static placeholder).
- A couple of screens (`WifiReconExtraRoutes.kt`'s `ProbeMonitorRoute`/
  `ChannelAnalyzerRoute`) are now unused leftovers — harmless, but you can
  delete them if you want to tidy up further.
