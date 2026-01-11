package com.trezoa.api

import com.trezoa.core.PublicKey
import com.trezoa.networking.RpcRequest
import com.trezoa.networking.TrezoaResponseSerializer
import com.trezoa.networking.makeRequestResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.*

class GetBalanceRequest(accountAddress: String) : RpcRequest() {
    override val method: String = "getBalance"
    override val params = buildJsonArray {
        add(accountAddress)
    }
}

internal fun GetBalanceSerializer() = TrezoaResponseSerializer(Long.serializer())

suspend fun Api.getBalance(account: PublicKey): Result<Long> =
    router.makeRequestResult(GetBalanceRequest(account.toBase58()), GetBalanceSerializer())
        .let { result ->
            @Suppress("UNCHECKED_CAST")
            if (result.isSuccess && result.getOrNull() == null)
                Result.failure(Error("Can not be null"))
            else result as Result<Long> // safe cast, null case handled above
        }

fun Api.getBalance(account: PublicKey, onComplete: ((Result<Long>) -> Unit)) {
    CoroutineScope(dispatcher).launch {
        onComplete(getBalance(account))
    }
}