package com.trezoa.rxtrezoa.actions

import com.trezoa.actions.Action
import com.trezoa.core.PublicKey
import com.trezoa.models.buffer.Mint
import com.trezoa.programs.TokenProgram
import io.reactivex.Single
import io.reactivex.disposables.Disposables
import com.trezoa.actions.getMintData
import com.trezoa.actions.getMultipleMintDatas

fun Action.getMintData(
    mintAddress: PublicKey,
    programId: PublicKey = TokenProgram.PROGRAM_ID,
): Single<Mint> {
    return Single.create { emitter ->
        this.getMintData(mintAddress, programId) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Action.getMultipleMintDatas(
    mintAddresses: List<PublicKey>,
    programId: PublicKey = TokenProgram.PROGRAM_ID,
): Single<Map<PublicKey, Mint>> {
    return Single.create { emitter ->
        this.getMultipleMintDatas(mintAddresses, programId) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}
