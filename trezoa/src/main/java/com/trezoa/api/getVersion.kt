package com.trezoa.api

import com.trezoa.networking.RpcRequest
import com.trezoa.networking.makeRequestResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class GetVersionRequest : RpcRequest() {
    override val method: String = "getVersion"
}

@Serializable
data class TrezoaVersion (
    @SerialName("trezoa-core")
    val trezoaCore: String,

    @SerialName("feature-set")
    val featureSet: Long
)

internal fun GetVersionSerializer() = TrezoaVersion.serializer()

fun Api.getVersion(onComplete: ((Result<TrezoaVersion>) -> Unit)) {
    CoroutineScope(dispatcher).launch {
        onComplete(getVersion())
    }
}

suspend fun Api.getVersion(): Result<TrezoaVersion> =
    router.makeRequestResult(GetVersionRequest(), GetVersionSerializer())
        .let { result ->
            @Suppress("UNCHECKED_CAST")
            if (result.isSuccess && result.getOrNull() == null)
                Result.failure(Error("Can not be null"))
            else result as Result<TrezoaVersion> // safe cast, null case handled above
        }