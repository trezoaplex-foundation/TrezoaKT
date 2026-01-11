package com.trezoa.rxtrezoa.actions

import com.trezoa.actions.Action
import com.trezoa.actions.sendTPLTokens
import com.trezoa.core.Account
import com.trezoa.core.PublicKey
import io.reactivex.Single
import io.reactivex.disposables.Disposables

fun Action.sendTPLTokens(
    account: Account,
    mintAddress: PublicKey,
    fromPublicKey: PublicKey,
    destinationAddress: PublicKey,
    allowUnfundedRecipient: Boolean = false,
    amount: Long

): Single<String> {
    return Single.create { emitter ->
        this.sendTPLTokens(
            mintAddress = mintAddress,
            fromPublicKey = fromPublicKey,
            destinationAddress = destinationAddress,
            amount = amount,
            allowUnfundedRecipient = allowUnfundedRecipient,
            account = account

        ) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}