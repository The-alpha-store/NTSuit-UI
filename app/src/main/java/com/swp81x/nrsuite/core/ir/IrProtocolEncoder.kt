package com.swp81x.nrsuite.core.ir

// Real protocol encoding was removed for this UI-only build. Always reports
// a placeholder failure so buttons render in their disabled/empty state.
object IrProtocolEncoder {
    fun encode(signal: IrSignal): IrEncodeResult =
        IrEncodeResult.Failure("IR transmission is not available in this UI preview build.")
}
