package com.trezoa.rxtrezoa.actions

import com.trezoa.actions.Action
import com.trezoa.actions.closeTokenAccount
import com.trezoa.core.Account
import com.trezoa.core.PublicKey
import io.reactivex.Single
import io.reactivex.disposables.Disposables

fun Action.closeTokenAccount(
    account: Account,
    tokenPubkey: PublicKey,
) : Single<Pair<String, PublicKey>> {
    return Single.create { emitter ->
        this.closeTokenAccount(account, tokenPubkey) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}