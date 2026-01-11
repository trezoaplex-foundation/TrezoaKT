package com.trezoa.api

import com.trezoa.core.PublicKey
import com.trezoa.models.RpcSendTransactionConfig
import com.trezoa.networking.*
import com.trezoa.networking.serialization.serializers.base64.BorshAsBase64JsonArraySerializer
import com.trezoa.networking.serialization.serializers.trezoa.AnchorAccountSerializer
import com.trezoa.networking.serialization.serializers.trezoa.TrezoaResponseSerializer
import com.trezoa.vendor.ResultError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

class AccountRequest(
    accountAddress: String,
    encoding: Encoding = Encoding.base64,
    commitment: String = Commitment.MAX.toString(),
    length: Int? = null,
    offset: Int? = length?.let { 0 }
) : RpcRequest() {

    constructor(account: String, transactionOptions: TransactionOptions) : this(account,
        transactionOptions.encoding, commitment = transactionOptions.commitment.toString())

    override val method = "getAccountInfo"
    override val params = buildJsonArray {
        add(accountAddress)
        addJsonObject {
            put("encoding", encoding.getEncoding())
            put("commitment", commitment)
            length?.let {
                putJsonObject("dataSlice") {
                    put("length", length)
                    put("offset", offset)
                }
            }
        }
    }
}

@Serializable
data class AccountInfo<D>(val data: D?, val executable: Boolean,
                          val lamports: Long, val owner: String?, val rentEpoch: Long)

fun <A> TrezoaAccountSerializer(serializer: KSerializer<A>) =
    AccountInfoSerializer(
        BorshAsBase64JsonArraySerializer(
            AnchorAccountSerializer(serializer.descriptor.serialName, serializer)
        )
    )

fun <D> AccountInfoSerializer(serializer: KSerializer<D>) =
    TrezoaResponseSerializer(AccountInfo.serializer(serializer))

inline fun <reified A> TrezoaAccountSerializer() =
    AccountInfoSerializer<A?>(BorshAsBase64JsonArraySerializer(AnchorAccountSerializer()))

inline fun <reified T> Api.getAccountInfo(
    serializer: KSerializer<T>,
    account: PublicKey,
    commitment: Commitment = Commitment.MAX,
    encoding: RpcSendTransactionConfig.Encoding = Encoding.base64,
    length: Int? = null,
    offset: Int? = length?.let { 0 },
    crossinline onComplete: ((Result<T>) -> Unit)
) {
    CoroutineScope(dispatcher).launch {
        onComplete(getAccountInfo(serializer, account, commitment, encoding, length, offset))
    }
}

suspend inline fun <reified A> Api.getAccountInfo(
    serializer: KSerializer<A>,
    account: PublicKey,
    commitment: Commitment = Commitment.MAX,
    encoding: Encoding = Encoding.base64,
    length: Int? = null,
    offset: Int? = length?.let { 0 }
): Result<A> {


    return router.makeRequestResult(
        AccountRequest(
            accountAddress = account.toBase58(),
            encoding = encoding,
            commitment = commitment.toString(),
            length = length,
            offset = offset
        ),
        serializer
    ).let { result ->
        @Suppress("UNCHECKED_CAST")
        if (result.isSuccess && result.getOrNull() == null)
            Result.failure(nullValueError)
        else result as Result<A> // safe cast, null case handled above
    }
}
val nullValueError = ResultError("Account return Null")
