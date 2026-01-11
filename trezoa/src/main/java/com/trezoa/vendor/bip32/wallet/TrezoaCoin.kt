package com.trezoa.vendor.bip32.wallet

import com.trezoa.vendor.bip32.wallet.key.TrezoaCurve

class TrezoaCoin {
    /**
     * Get the curve
     *
     * @return curve
     */
    val curve = TrezoaCurve()

    /**
     * get the coin type
     *
     * @return coin type
     */
    val coinType: Long = 501

    /**
     * get the coin purpose
     *
     * @return purpose
     */
    val purpose: Long = 44

    /**
     * get whether the addresses must always be hardened
     *
     * @return always hardened
     */
    val alwaysHardened = true
}