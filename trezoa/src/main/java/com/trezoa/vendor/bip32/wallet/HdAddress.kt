package com.trezoa.vendor.bip32.wallet

import com.trezoa.vendor.bip32.wallet.key.HdPrivateKey
import com.trezoa.vendor.bip32.wallet.key.HdPublicKey

/**
 * An HD pub/private key
 */
class HdAddress(
    val privateKey: HdPrivateKey,
    val publicKey: HdPublicKey,
    val coinType: TrezoaCoin,
    val path: String
)