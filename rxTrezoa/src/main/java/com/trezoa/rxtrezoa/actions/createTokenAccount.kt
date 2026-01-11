package com.trezoa.rxtrezoa.actions

import com.trezoa.actions.Action
import com.trezoa.actions.createTokenAccount
import com.trezoa.core.Account
import com.trezoa.core.PublicKey
import io.reactivex.Single
import io.reactivex.disposables.Disposables

fun Action.createTokenAccount(
    account: Account,
    mintAddress: PublicKey
) : Single<Pair<String, PublicKey>> {
    return Single.create { emitter ->
        this.createTokenAccount(account, mintAddress) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}