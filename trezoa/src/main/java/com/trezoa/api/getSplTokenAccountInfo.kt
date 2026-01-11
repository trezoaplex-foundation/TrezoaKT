package com.trezoa.api

import com.trezoa.core.PublicKey
import com.trezoa.models.RpcSendTransactionConfig
import com.trezoa.networking.serialization.serializers.trezoa.TrezoaResponseSerializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class SplTokenAccountTokenInfo (
    val isNative: Boolean?,
    val mint: String?,
    val owner: String?,
    val state: String?,
    val tokenAmount: TokenAmountInfo?
)

@Serializable
data class SplTokenAccountInfoParsedData (
    val info: SplTokenAccountTokenInfo,
    val type: String
)

@Serializable
data class SplTokenAccountInfo (
    val parsed: SplTokenAccountInfoParsedData,
    val program: String,
    val space: Int? = null
)

@Serializable
data class SplTokenAccountValue (
    val data: SplTokenAccountInfo
)

suspend fun Api.getSplTokenAccountInfo(account: PublicKey): Result<SplTokenAccountInfo> =
    this.getAccountInfo(TrezoaResponseSerializer(SplTokenAccountValue.serializer()), account, encoding = RpcSendTransactionConfig.Encoding.jsonParsed).map { result ->
        (result?.data ?: throw Error("Can not be null"))
    }

fun Api.getSplTokenAccountInfo(account: PublicKey, onComplete: (Result<SplTokenAccountInfo>) -> Unit) {
    CoroutineScope(dispatcher).launch {
        onComplete(getSplTokenAccountInfo(account))
    }
}