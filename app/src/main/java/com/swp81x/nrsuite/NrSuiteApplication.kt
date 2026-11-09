package com.swp81x.nrsuite

import android.app.Application

/**
 * UI-only preview build: no background services, no USB/BLE device
 * management, no notification channels. This class exists only so
 * MainActivity has an Application subclass declared in the manifest.
 */
class NrSuiteApplication : Application()
