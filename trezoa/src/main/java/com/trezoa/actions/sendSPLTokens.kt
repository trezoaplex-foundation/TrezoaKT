package com.trezoa.actions

import com.trezoa.core.Account
import com.trezoa.core.PublicKey
import com.trezoa.core.Transaction
import com.trezoa.programs.AssociatedTokenProgram
import com.trezoa.programs.TokenProgram
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

suspend fun Action.sendTPLTokens(
    mintAddress: PublicKey,
    fromPublicKey: PublicKey,
    destinationAddress: PublicKey,
    amount: Long,
    allowUnfundedRecipient: Boolean = false,
    account: Account
): Result<String>{
    val spl = this.findTPLTokenDestinationAddress(
        mintAddress,
        destinationAddress,
        allowUnfundedRecipient
    ).getOrThrows()

    val toPublicKey = spl.first
    val isUnregisteredAsocciatedToken = spl.second

    val transaction = Transaction()

    // create associated token address
    if(isUnregisteredAsocciatedToken) {
        val mint = mintAddress
        val owner = destinationAddress
        val createATokenInstruction = AssociatedTokenProgram.createAssociatedTokenAccountInstruction(
            mint =  mint,
            associatedAccount = toPublicKey,
            owner = owner,
            payer = account.publicKey
        )
        transaction.add(createATokenInstruction)
    }

    // send instruction
    val sendInstruction = TokenProgram.transfer(fromPublicKey,toPublicKey, amount, account.publicKey)
    transaction.add(sendInstruction)
    return serializeAndSendWithFee(transaction, listOf(account))
}

fun Action.sendTPLTokens(
    mintAddress: PublicKey,
    fromPublicKey: PublicKey,
    destinationAddress: PublicKey,
    amount: Long,
    allowUnfundedRecipient: Boolean = false,
    account: Account,
    onComplete: ((Result<String>) -> Unit)
){
    CoroutineScope(api.dispatcher).launch {
        onComplete(sendTPLTokens(mintAddress, fromPublicKey, destinationAddress, amount, allowUnfundedRecipient, account))
    }
}