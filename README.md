# NRSuite — UI Preview Build

This is a **UI-only** fork of the NRSuite Android app. It keeps every screen,
layout, and visual component from the original project, but every actual
capability has been removed:

- No WiFi/BLE/USB/RFID/IR hardware access
- No packet capture, injection, or protocol logic
- No cracking, bruteforcing, or credential handling of any kind
- No background services, notifications, or network calls

All screens render against static placeholder state, and every action
button is a no-op. This build exists purely to preview, demo, or iterate on
the interface and navigation.

## What's here

- `app/src/main/java/com/swp81x/nrsuite/ui/` — every original screen,
  unchanged.
- `app/src/main/java/com/swp81x/nrsuite/core/` — plain data/model classes
  the screens need to compile (no logic).
- `app/src/main/java/com/swp81x/nrsuite/ui/NRSuiteApp.kt` — a new, much
  smaller navigation shell (bottom nav + a route per screen) that replaces
  the original ViewModel-driven wiring.

## Building

```
./gradlew assembleDebug
```

A GitHub Actions workflow (`.github/workflows/android-build.yml`) builds a
debug APK on every push/PR and uploads it as a build artifact.

## License

See [LICENSE](./LICENSE).
