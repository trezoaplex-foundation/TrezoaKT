package com.trezoa.api

import com.trezoa.core.PublicKey
import com.trezoa.networking.RpcRequest
import com.trezoa.networking.makeRequestResult
import com.trezoa.networking.serialization.serializers.trezoa.PublicKeyAsStringSerializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class GetSlotLeaderRequest : RpcRequest() {
    override val method: String = "getSlotLeader"
}

internal fun GetSlotLeaderSerializer() = PublicKeyAsStringSerializer

suspend fun Api.getSlotLeader(): Result<PublicKey> =
    router.makeRequestResult(GetSlotLeaderRequest(), GetSlotLeaderSerializer())
        .let { result ->
            @Suppress("UNCHECKED_CAST")
            if (result.isSuccess && result.getOrNull() == null)
                Result.failure(Error("Can not be null"))
            else result as Result<PublicKey> // safe cast, null case handled above
        }

fun Api.getSlotLeader(onComplete: (Result<PublicKey>) -> Unit) {
    CoroutineScope(dispatcher).launch {
        onComplete(getSlotLeader())
    }
}