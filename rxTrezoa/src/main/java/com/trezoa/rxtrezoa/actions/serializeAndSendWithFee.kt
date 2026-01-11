package com.trezoa.rxtrezoa.actions

import com.trezoa.actions.Action
import com.trezoa.actions.serializeAndSendWithFee
import com.trezoa.core.Account
import com.trezoa.core.Transaction
import io.reactivex.Single
import io.reactivex.disposables.Disposables

fun Action.serializeAndSendWithFee(
    transaction: Transaction,
    signers: List<Account>,
    recentBlockHash: String? = null
): Single<String> {
    return Single.create { emitter ->
        this.serializeAndSendWithFee(transaction, signers, recentBlockHash) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}
