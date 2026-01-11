package com.trezoa.actions

import com.trezoa.api.sendTransaction
import com.trezoa.core.Account
import com.trezoa.core.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Action.serializeAndSendWithFee(transaction: Transaction,
                                   signers: List<Account>,
                                   recentBlockHash: String? = null,
                                   onComplete: ((Result<String>) -> Unit)
) {
    CoroutineScope(api.dispatcher).launch {
        onComplete(serializeAndSendWithFee(transaction, signers, recentBlockHash))
    }
}

suspend fun Action.serializeAndSendWithFee(
    transaction: Transaction,
    signers: List<Account>,
    recentBlockHash: String? = null
): Result<String> = api.sendTransaction(transaction, signers, recentBlockHash)