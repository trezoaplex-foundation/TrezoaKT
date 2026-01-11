package com.trezoa.actions

import com.trezoa.core.HotAccount
import com.trezoa.core.PublicKey
import com.trezoa.core.Transaction
import com.trezoa.models.REQUIRED_ACCOUNT_SPACE
import com.trezoa.programs.SystemProgram
import com.trezoa.programs.TokenProgram
import com.trezoa.api.getMinimumBalanceForRentExemption
import com.trezoa.core.Account
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Action.createTokenAccount(
    account: Account,
    mintAddress: PublicKey,
    onComplete: ((Result<Pair<String, PublicKey>>) -> Unit)
) {
    CoroutineScope(dispatcher).launch {
        onComplete(createTokenAccount(account, mintAddress))
    }
}

suspend fun Action.createTokenAccount(
    account: Account,
    mintAddress: PublicKey,
) : Result<Pair<String, PublicKey>> {

    val balance = api.getMinimumBalanceForRentExemption(REQUIRED_ACCOUNT_SPACE).getOrElse {
        return Result.failure(it)
    }

    val transaction = Transaction()
    val newAccount = HotAccount()
    val createAccountInstruction = SystemProgram.createAccount(
        fromPublicKey = account.publicKey,
        newAccountPublickey = newAccount.publicKey,
        lamports = balance,
        REQUIRED_ACCOUNT_SPACE,
        TokenProgram.PROGRAM_ID
    )

    transaction.add(createAccountInstruction)
    val initializeAccountInstruction = TokenProgram.initializeAccount(
        account = newAccount.publicKey,
        mint = mintAddress,
        owner = account.publicKey
    )

    transaction.add(initializeAccountInstruction)
    val transactionId = this.serializeAndSendWithFee(transaction, listOf(account,newAccount), null).getOrElse {
        return Result.failure(it)
    }

    return Result.success(Pair(transactionId, newAccount.publicKey))
}