package com.swp81x.nrsuite.core.usb

import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager

/**
 * UI-only preview build: no USB serial driver library is bundled, so device
 * discovery always reports an empty list. The shape is kept because many
 * screens take a [UsbSerialDevice] list as part of their state.
 */
data class UsbSerialDevice(
    val device: UsbDevice,
    val chipLabel: String? = null,
) {
    val displayName: String
        get() {
            val base = device.productName?.takeIf { it.isNotBlank() } ?: "USB Serial"
            return chipLabel?.let { "$base ($it)" } ?: base
        }
}

object UsbSerialDeviceCatalog {
    fun list(usbManager: UsbManager): List<UsbSerialDevice> = emptyList()

    fun find(usbManager: UsbManager, device: UsbDevice): UsbSerialDevice? = null
}
