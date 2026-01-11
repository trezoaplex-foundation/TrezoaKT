package com.trezoa

import com.trezoa.actions.Action
import com.trezoa.api.Api
import com.trezoa.models.Token
import com.trezoa.networking.NetworkingRouter
import com.trezoa.networking.socket.TrezoaSocket
import com.trezoa.vendor.TokensListParser

 class Trezoa(val router: NetworkingRouter){
    val api: Api = Api(router)
    val socket: TrezoaSocket = TrezoaSocket(router.endpoint)
    val supportedTokens: List<Token> by lazy {
        TokensListParser().parse(router.endpoint.network.name).getOrThrows()
    }
    val action: Action = Action(api, supportedTokens)
}
