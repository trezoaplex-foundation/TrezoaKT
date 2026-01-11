package com.trezoa.actions

import com.trezoa.api.*
import com.trezoa.core.PublicKey
import com.trezoa.core.PublicKey.Companion.createProgramAddress
import com.trezoa.models.buffer.AccountInfoData
import com.trezoa.networking.serialization.serializers.base64.BorshAsBase64JsonArraySerializer
import com.trezoa.programs.SystemProgram
import com.trezoa.programs.TokenProgram
import com.trezoa.vendor.ResultError
import com.trezoa.vendor.ContResult
import com.trezoa.vendor.Result
import com.trezoa.vendor.flatMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

typealias TPLTokenDestinationAddress = Pair<PublicKey, Boolean>

suspend fun Action.findTPLTokenDestinationAddress(
    mintAddress: PublicKey,
    destinationAddress: PublicKey,
    allowUnfundedRecipient: Boolean = false,
): Result<TPLTokenDestinationAddress, ResultError> {
    return if(allowUnfundedRecipient) {
        checkTPLTokenAccountExistence(
            mintAddress,
            destinationAddress,
        )
    } else {
        findTPLTokenDestinationAddressOfExistingAccount(
            mintAddress,
            destinationAddress,
        )
    }
}

fun Action.findTPLTokenDestinationAddress(
    mintAddress: PublicKey,
    destinationAddress: PublicKey,
    allowUnfundedRecipient: Boolean = false,
    onComplete: ((Result<TPLTokenDestinationAddress, ResultError>) -> Unit)
){
    CoroutineScope(api.dispatcher).launch {
        onComplete(findTPLTokenDestinationAddress(mintAddress, destinationAddress, allowUnfundedRecipient))
    }
}

suspend fun Action.checkTPLTokenAccountExistence(
    mintAddress: PublicKey,
    destinationAddress: PublicKey
): Result<TPLTokenDestinationAddress, ResultError> {
    var associatedTokenAddress: PublicKey? = null
    try {
        val associatedProgramDerivedAddress = PublicKey.associatedTokenAddress(destinationAddress, mintAddress).address
        associatedTokenAddress = associatedProgramDerivedAddress
    } catch (error: Exception){
        return Result.failure(error)
    }

    var hasAssociatedTokenAccount = false
    val result = this.api.getSplTokenAccountInfo(associatedTokenAddress)
    result.onSuccess {
        hasAssociatedTokenAccount = true
    }.onFailure { error ->
        if(error.message == nullValueError.message){
            hasAssociatedTokenAccount = false
        } else {
            return Result.failure(ResultError(error))
        }
    }
    return Result.success(TPLTokenDestinationAddress(associatedTokenAddress, !hasAssociatedTokenAccount))
}

private fun Action.checkTPLTokenAccountExistence(
    mintAddress: PublicKey,
    destinationAddress: PublicKey,
    onComplete: ((Result<TPLTokenDestinationAddress, ResultError>) -> Unit)
) {
    CoroutineScope(api.dispatcher).launch {
        onComplete(checkTPLTokenAccountExistence(mintAddress, destinationAddress))
    }
}

suspend fun Action.findTPLTokenDestinationAddressOfExistingAccount(
    mintAddress: PublicKey,
    destinationAddress: PublicKey
): Result<TPLTokenDestinationAddress, ResultError> {
    val infoResult = this.api.getAccountInfo(
        AccountInfoSerializer(
            BorshAsBase64JsonArraySerializer((AccountInfoData.serializer()))),
        destinationAddress,
    )

    val info = infoResult.getOrElse {
        return Result.failure(Exception(it))
    }

    // Its en existing account
    val toTokenMint = info?.data?.mint?.toBase58()
    var toPublicKeyString = ""
    if (info?.owner == TokenProgram.PROGRAM_ID.toBase58() && mintAddress.toBase58() == toTokenMint) { // detect if destination address is already a TPLToken address
        toPublicKeyString = destinationAddress.toBase58()
    }else if (info?.owner == SystemProgram.PROGRAM_ID.toBase58()) { // detect if destination address is a TRZ address
        val address = createProgramAddress(
            listOf(destinationAddress.toByteArray()),
            mintAddress
        )
        toPublicKeyString = address.toBase58()
    }
    val toPublicKey = PublicKey(toPublicKeyString)

    return if(destinationAddress.toBase58() != toPublicKey.toBase58()){
        val info1 = this.api.getAccountInfo(
            TrezoaAccountSerializer(AccountInfoData.serializer()),
            toPublicKey
        ).getOrThrow()
        var isUnregisteredAsocciatedToken = true
        // if associated token account has been registered
        if(info1?.owner == TokenProgram.PROGRAM_ID.toBase58() &&
            info?.data != null) {
            isUnregisteredAsocciatedToken = false
        }
        Result.success(TPLTokenDestinationAddress(toPublicKey,isUnregisteredAsocciatedToken))
    } else {
        Result.success(TPLTokenDestinationAddress(toPublicKey, false))
    }
}