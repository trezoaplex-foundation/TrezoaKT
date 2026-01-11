package com.trezoa.api

import com.trezoa.core.PublicKey
import com.trezoa.networking.RpcRequest
import com.trezoa.networking.makeRequestResult
import com.trezoa.networking.serialization.serializers.trezoa.PublicKeyAsStringSerializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class GetIdentityBlockRequest : RpcRequest() {
    override val method: String = "getIdentity"
}

@Serializable
data class GetIdentityResponse (
    @Serializable(with = PublicKeyAsStringSerializer::class) val identity: PublicKey
)

internal fun GetIdentitySerializer() = GetIdentityResponse.serializer()

fun Api.getIdentity(onComplete: (Result<PublicKey>) -> Unit) {
    CoroutineScope(dispatcher).launch {
        onComplete(getIdentity())
    }
}

suspend fun Api.getIdentity(): Result<PublicKey> =
    router.makeRequestResult(GetIdentityBlockRequest(), GetIdentitySerializer())
        .let { result ->
            @Suppress("UNCHECKED_CAST")
            if (result.isSuccess && result.getOrNull() == null)
                Result.failure(Error("Can not be null"))
            else result.map {
                it!!.identity
            } as Result<PublicKey> // safe cast, null case handled above
        }