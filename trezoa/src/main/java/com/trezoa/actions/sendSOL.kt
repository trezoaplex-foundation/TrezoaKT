package com.trezoa.actions

import com.trezoa.api.sendTransaction
import com.trezoa.core.Account
import com.trezoa.core.PublicKey
import com.trezoa.core.Transaction
import com.trezoa.programs.SystemProgram
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Action.sendTRZ(
    account: Account,
    destination: PublicKey,
    amount: Long,
    onComplete: ((Result<String>) -> Unit)
) {
    CoroutineScope(dispatcher).launch {
        onComplete(sendTRZ(account, destination, amount))
    }
}

suspend fun Action.sendTRZ(
    account: Account,
    destination: PublicKey,
    amount: Long
): Result<String> {
    val instructions = SystemProgram.transfer(account.publicKey, destination, amount)
    val transaction = Transaction()
    transaction.add(instructions)
    val transactionId = api.sendTransaction(transaction, listOf(account)).getOrElse {
        return Result.failure(it)
    }
    return Result.success(transactionId)
}