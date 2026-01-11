package com.trezoa

import com.trezoa.networking.HttpNetworkingRouter
import com.trezoa.networking.Network
import com.trezoa.networking.RPCEndpoint
import com.trezoa.networking.socket.TrezoaSocket
import com.trezoa.trezoa.BuildConfig
import java.net.URL

object TrezoaTestsUtils {
    const val RPC_URL = BuildConfig.RPC_URL
}

fun TrezoaTestsUtils.generateTrezoaConnection() =
    Trezoa(
        HttpNetworkingRouter(
            RPCEndpoint.custom(
                URL(RPC_URL),
                URL(RPC_URL),
                Network.devnet
            )
        )
    )

fun TrezoaTestsUtils.generateTrezoaSocket() = TrezoaSocket(
    RPCEndpoint.custom(
        URL(RPC_URL),
        URL(RPC_URL),
        Network.devnet
    ),
    enableDebugLogs = true
)