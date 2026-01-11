package com.trezoa.rxtrezoa.actions

import com.trezoa.actions.Action
import com.trezoa.actions.TPLTokenDestinationAddress
import com.trezoa.actions.findTPLTokenDestinationAddress
import com.trezoa.core.PublicKey
import io.reactivex.Single
import io.reactivex.disposables.Disposables

fun Action.findTPLTokenDestinationAddress(
    mintAddress: PublicKey,
    destinationAddress: PublicKey,
) : Single<TPLTokenDestinationAddress> {
    return Single.create { emitter ->
        this.findTPLTokenDestinationAddress(mintAddress, destinationAddress) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}