package com.trezoa.networking

import java.net.URL

const val MAINNETBETA = "mainnet-beta"
const val DEVNET = "devnet"
const val TESTNET = "testnet"

sealed class Network(val name: String) {
    object mainnetBeta: Network(MAINNETBETA)
    object devnet: Network(DEVNET)
    object testnet: Network(TESTNET)
    var cluster: String = this.name
}


sealed class RPCEndpoint(open val url: URL, open val urlWebSocket: URL, open val network: Network) {
    object mainnetBetaTrezoa: RPCEndpoint(URL("https://api.mainnet-beta.trezoa.com"), URL("https://api.mainnet-beta.trezoa.com"), Network.mainnetBeta)
    object devnetTrezoa: RPCEndpoint(URL("https://api.devnet.trezoa.com"), URL("https://api.devnet.trezoa.com"), Network.devnet)
    object testnetTrezoa: RPCEndpoint(URL("https://testnet.trezoa.com"), URL("https://testnet.trezoa.com"),Network.testnet)
    data class custom(override val url: URL, override val urlWebSocket: URL, override val network: Network) : RPCEndpoint(url, urlWebSocket, network)
}