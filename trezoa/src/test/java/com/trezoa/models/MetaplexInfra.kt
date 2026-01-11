package com.trezoa.models

import com.trezoa.core.PublicKey
import com.trezoa.networking.serialization.serializers.trezoa.PublicKeyAs32ByteSerializer
import kotlinx.serialization.Serializable

@Serializable
data class TrezoaplexMeta(
    val key: Byte,
    @Serializable(with = PublicKeyAs32ByteSerializer::class) val update_authority: PublicKey,
    @Serializable(with = PublicKeyAs32ByteSerializer::class) val mint: PublicKey,
    val data: TrezoaplexData
)

@Serializable
data class TrezoaplexData(
    val name: String,
    val symbol: String,
    val uri: String
)