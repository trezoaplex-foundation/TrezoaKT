package com.trezoa.api

import com.trezoa.core.Account
import com.trezoa.core.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

suspend fun Api.sendTransaction(
    transaction: Transaction,
    signers: List<Account>,
    recentBlockHash: String? = null
): Result<String> {
    val blockHash = recentBlockHash ?: getRecentBlockhash().getOrThrow()
    transaction.setRecentBlockHash(blockHash)
    transaction.sign(signers)
    val serialized = transaction.serialize()

    return sendRawTransaction(serialized)
}

fun Api.sendTransaction(
    transaction: Transaction,
    signers: List<Account>,
    recentBlockHash: String? = null,
    onComplete: ((Result<String>) -> Unit)
) {
    CoroutineScope(dispatcher).launch {
        onComplete(sendTransaction(transaction, signers, recentBlockHash))
    }
}

