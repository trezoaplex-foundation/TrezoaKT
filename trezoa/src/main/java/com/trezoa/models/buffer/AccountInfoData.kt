package com.trezoa.models.buffer

import com.trezoa.core.PublicKey
import com.trezoa.networking.serialization.serializers.trezoa.PublicKeyAs32ByteSerializer
import kotlinx.serialization.Serializable

@Serializable
data class AccountInfoData(
    @Serializable(with = PublicKeyAs32ByteSerializer::class) val mint: PublicKey,
    @Serializable(with = PublicKeyAs32ByteSerializer::class) val owner: PublicKey,
    val lamports: Long,
    val delegateOption: Int,
    @Serializable(with = PublicKeyAs32ByteSerializer::class) val delegate: PublicKey?,
    val isInitialized: Boolean,
    val isFrozen: Boolean,
    val state: Int,
    val isNativeOption: Int,
    val rentExemptReserve: Long?,
    val isNativeRaw: Long,
    val isNative: Boolean,
    val delegatedAmount: Long,
    val closeAuthorityOption: Int,
    @Serializable(with = PublicKeyAs32ByteSerializer::class) val closeAuthority: PublicKey?
)