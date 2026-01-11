package com.trezoa.vendor

import com.trezoa.models.Token
import com.trezoa.models.TokenTag
import com.trezoa.models.TokensList
import com.trezoa.resources.devnet
import com.trezoa.resources.mainnet_beta
import com.trezoa.resources.testnet
import kotlinx.serialization.json.Json


class TokensListParser {

    private val json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    private val tokens by lazy {
        mapOf(
            "devnet" to devnet,
            "mainnet-beta" to mainnet_beta,
            "testnet" to testnet
        )
    }

    fun parse(network: String): Result<List<Token>, ResultError> {
        val jsonContent = tokens[network]

        return try {
            val tokenList = json.decodeFromString(TokensList.serializer(), jsonContent!!)
            tokenList.tokens = tokenList.tokens.map {
                it.tokenTags = it._tags.map { index ->
                    tokenList.tags[index] ?: TokenTag(it.name, it.name)
                }
                it
            }
            val listTokens = tokenList.tokens.fold(listOf()) { list: List<Token>, token: Token ->
                var result = list.toMutableList()
                if (!result.contains(token)) {
                    result.add(token)
                }
                result.toList()
            }
            Result.success(listTokens)
        } catch (e: Exception) {
            Result.failure(ResultError(e))
        }
    }
}