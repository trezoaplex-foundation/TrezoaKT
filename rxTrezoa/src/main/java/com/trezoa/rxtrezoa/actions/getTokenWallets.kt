package com.trezoa.rxtrezoa.actions

import com.trezoa.actions.Action
import com.trezoa.actions.getTokenWallets
import com.trezoa.core.PublicKey
import com.trezoa.models.Wallet
import io.reactivex.Single
import io.reactivex.disposables.Disposables

fun Action.getTokenWallets(
    account: PublicKey,
): Single<List<Wallet>> {
    return Single.create { emitter ->
        this.getTokenWallets(account ) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}
