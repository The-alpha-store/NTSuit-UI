package com.swp81x.nrsuite.core.eapol

data class EapolHandshake(
    var m1: Boolean = false,
    var m2: Boolean = false,
    var m3: Boolean = false,
    var m4: Boolean = false,
) {
    val isComplete: Boolean
        get() = m1 && m2 && m3 && m4
}
