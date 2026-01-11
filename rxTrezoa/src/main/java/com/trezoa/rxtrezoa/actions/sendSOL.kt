package com.trezoa.rxtrezoa.actions

import com.trezoa.actions.Action
import com.trezoa.actions.sendTRZ
import com.trezoa.core.Account
import com.trezoa.core.PublicKey
import io.reactivex.Single
import io.reactivex.disposables.Disposables

fun Action.sendTRZ(
    account: Account,
    destination: PublicKey,
    amount: Long
): Single<String> {
    return Single.create { emitter ->
        this.sendTRZ(account, destination, amount) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}
