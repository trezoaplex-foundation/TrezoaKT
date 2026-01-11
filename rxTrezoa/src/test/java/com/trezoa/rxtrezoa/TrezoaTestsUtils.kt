package com.trezoa.rxtrezoa

import com.trezoa.Trezoa
import com.trezoa.networking.HttpNetworkingRouter
import com.trezoa.networking.Network
import com.trezoa.networking.RPCEndpoint
import com.trezoa.trezoa.BuildConfig
import java.net.URL

object TrezoatestsUtils {
    const val RPC_URL = BuildConfig.RPC_URL
}

fun TrezoatestsUtils.generateTrezoaConnection() =
    Trezoa(
        HttpNetworkingRouter(
            RPCEndpoint.custom(
                URL(RPC_URL),
                URL(RPC_URL),
                Network.devnet
            )
        )
    )
