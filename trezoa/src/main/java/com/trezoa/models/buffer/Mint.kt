package com.trezoa.models.buffer

import com.trezoa.core.PublicKey
import com.trezoa.networking.serialization.serializers.trezoa.PublicKeyAs32ByteSerializer
import kotlinx.serialization.Serializable

@Serializable
data class Mint(
    val mintAuthorityOption: Int,
    @Serializable(with = PublicKeyAs32ByteSerializer::class) val mintAuthority: PublicKey?,
    val supply: Long,
    val decimals: Int,
    val isInitialized: Boolean,
    val freezeAuthorityOption: Int,
    @Serializable(with = PublicKeyAs32ByteSerializer::class) val freezeAuthority: PublicKey?
)