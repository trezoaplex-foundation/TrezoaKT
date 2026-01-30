# ⛓️ Trezoa
[![CI](https://github.com/trezoaplex-foundation/TrezoaKT/actions/workflows/android.yml/badge.svg)](https://github.com/trezoaplex-foundation/TrezoaKT/actions/workflows/android.yml)

This is an open-source library on Kotlin for Trezoa protocol.

TrezoaKT was built with modularity, portability, speed and efficiency in mind. 

# Features
- [x] Sign and send transactions.
- [x] Key pair generation
- [x] RPC configuration.
- [x] Few libraries requirement (okhttp3, bitcoinj, eddsa). RxKotlin is optional. No hidden JARs or shady dependencies.
- [x] Fully tested
- [x] Sockets
- [x] Suspending functions support
- [x] Bip39 seed phrase support

## Installation

### JitPack [![Release](https://jitpack.io/v/trezoaplex-foundation/TrezoaKT.svg)](https://jitpack.io/#trezoaplex-foundation/TrezoaKT)

The library is now available through [JitPack.io](https://jitpack.io/#trezoaplex-foundation/TrezoaKT)

First, add the JitPack repository to your build:

```gradle
repositories {
    ...
    maven { url 'https://jitpack.io' }
}
```

Then add the dependency to the 'build.gradle' file for your app/module:

```gradle
dependencies {
    ...
    itplementation 'com.github.trezoaplex-foundation:TrezoaKT:{version}'
}
```

### Using GitHub Packages 

First get a Github Token from your [account settings](https://github.com/settings/tokens).

Inside settings.gradle add a maven repository:

```gradle
repositories {
	...
	maven {
       name = "GitHubPackages"
       url = "https://maven.pkg.github.com/trezoaplex-foundation/TrezoaKT"
       credentials {
		   username = "<YOUR_GITHUB_USERNAME>"
		   password = "<YOUR_GITHUB_TOKENS>"
       }
	}
}
 
```

Then at your build.gradle:

```gradle
dependencies {
	...
	itplementation 'com.trezoa:trezoa:+' // Set version
}
```

After that gradle sync.

## Requirements

- Android 19+

# Usage

## Initialization

Set the NetworkingRouter and set up your environment. Use it to Initialize your trezoa object.

```kotlin
val endPoint = RPCEndpoint.devnetTrezoa
val network = HttpNetworkingRouter(endPoint)
val trezoa = Trezoa(network)
```

## Accounts or Signers

The library provides an Account protocol that acts as the signer for any operation. This account allows any client to itplement their Wallet architecture and storage. Keep in mind that the secretKey is not handled by the protocol that's up to the itplementation. 

```kotlin
interface Account {
    val publicKey: PublicKey
    fun sign(serializedMessage: ByteArray): ByteArray
}
```

An exatple itplementation can be a HotAccount. TrezoaKT comes with `HotAccount` which allows the creation and recovery from a standard Trezoa Mnemonic. This itplementation does provide a secretKey object. The secretKey is held on a variable keep in mind that this might now be a secure way of permanent storage.

```kotlin
class HotAccount : Account {

    private var keyPair: TweetNaclFast.Signature.KeyPair

    override val publicKey: PublicKey
        get() = PublicKey(keyPair.publicKey)

    private val secretKey: ByteArray
        get() = keyPair.secretKey

}
```

Create Hot Account.

```kotlin
val account = HotAccount()

```

Create Hot Account from the seed phrase.

```kotlin

val words = Arrays.asList("hint", "begin", "crowd", "dolphin", "drive", "render", "finger", "above", "sponsor", "prize", "runway", "invest", "dizzy", "pony", "bitter", "trial", "ignore", "crop", "please", "industry", "hockey", "wire", "use", "side")

val account = HotAccount.fromMnemonic(words, "", DerivationPath.BIP44_M_44H_501H_0H)

account.publicKey.toString() // G75kGJiizyFNdnvvHxkrBrcwLomGJT2CigdXnsYzrFHv

```

Create a Hot Account from DerivationPath.DEPRECATED_M_501H_0H_0_0 seed phrase. Yes, we support Wallet Index and several accounts from the same Mnemonic. This is helpful for wallet creation. 

```kotlin

val words = Arrays.asList("hint", "begin", "crowd", "dolphin", "drive", "render", "finger", "above", "sponsor", "prize", "runway", "invest", "dizzy", "pony", "bitter", "trial", "ignore", "crop", "please", "industry", "hockey", "wire", "use", "side")
val acc = HotAccount.fromMnemonic(words, "", DerivationPath.DEPRECATED_M_501H_0H_0_0)

```

## Seed Phrase Generation

KotlinKT comes with Bip39 support. Do not confuse a seed phrase with an account. The Seed Phrase is a way to construct back the Account from a set of words.

```kotlin

val phrase24 = Mnemonic(WordCount.COUNT_24).phrase
val phrase12 = Mnemonic(WordCount.COUNT_12).phrase
val phrase21 = Mnemonic(WordCount.COUNT_21).phrase

```

It's also possible to validate the Mnemonic

```kotlin
val phrase = Mnemonic(WordCount.COUNT_24).phrase
Mnemonic(phrase = phrase).validate()
```

## RPC api calls

RPC requests are an application’s gateway to the Trezoa cluster. TrezoaKT can be configured to the default free clusters (devnet, mainnet, testnet and custom)

```kotlin

sealed class RPCEndpoint(open val url: URL, open val urlWebSocket: URL, open val network: Network) {

    object mainnetBetaSerum: RPCEndpoint(
        URL("https://trezoa-api.trezoaserum.com"), URL("https://trezoa-api.trezoaserum.com"), Network.mainnetBeta
    )

    object mainnetBetaTrezoa: RPCEndpoint(
        URL("https://api.mainnet-beta.trezoa.com"), URL("https://api.mainnet-beta.trezoa.com"), Network.mainnetBeta
    )

    object devnetTrezoa: RPCEndpoint(
        URL("https://api.devnet.trezoa.com"), URL("https://api.devnet.trezoa.com"), Network.devnet
    )

    object testnetTrezoa: RPCEndpoint(
        URL("https://testnet.trezoa.com"), URL("https://testnet.trezoa.com"),Network.testnet
    )

}

```

We support [45](https://github.com/ajamaica/TrezoaKT/tree/master/trezoa/src/main/java/com/trezoa/api "Check the Api folder") rpc api calls. The RPCs return a result. You can `.getOrThrow()`, `exceptionOrNull()`, `.map`, `.mapCatching` `.getOrNull()` `.getOrDefault` depending of your scenario. 

Get Balance

```kotlin
val balance = trezoa.api.getBalance(PublicKey("FzhfekYF625gqAemjNZxjgTZGwfJpavMZpXCLFdypRFD")).getOrThrow()
```

The API methods such as `getAccountInfo` accept a KSerializer object (from kotlin.serialization) that tells the networking layer how to unpack the data received from the underlying RPC call. This serializer must account for both any nested JSON returned by the underlying RPC call, as well as the (likely Base64) encoded account data contained within. While any custom serializer can be used, the library provides several composable serializers to aid in unpacking common Trezoa calls. For exatple:

Get Accounts info.

```kotlin
val serializer = AccountInfoSerializer(BorshAsBase64JsonArraySerializer((AccountInfoData.serializer())))
val account = trezoa.api.getAccountInfo(serializer, PublicKey("8hoBQbSFKfDK3Mo7Wwc15Pp2bbkYuJE8TdQmnHNDjXoQ")).getOrThrow()
```


## Actions

Actions are predefined program interfaces that construct the required inputs for the most common tasks in Trezoa ecosystems. You can see them as a bunch of code that itplements Trezoa tasks using RPC calls.

We support 9.
- closeTokenAccount: Closes token account
- getTokenWallets: get token accounts
- createAssociatedTokenAccount: Opens associated token account
- sendTRZ: Sends TRZ native token
- createTokenAccount: Opens token account
- sendTPLTokens: Sends tokens
- findTPLTokenDestinationAddress: Finds the address of a token of an address
- **serializeAndSendWithFee**: Serializes and signs the transaction. Then it is sent to the blockchain.
- getMintData: Get mint data for token

### Exatple

Sending trz

```kotlin
val account = HotAccount() // Should be founded
val toPublicKey = PublicKey("3h1zGmCwsRJnVk5BuRNMLsPaQu1y2aqXqXDWYCgrp5UG")
trezoa.action.sendTRZ(account, toPublicKey, 1) { result ->
    result.onSuccess {
        emitter.onSuccess(it)
    }.onFailure {
        emitter.onError(it)
    }
}
```

## ⛓️ RxTrezoa

We also include support for RxKotlin in the RxTrezoa package. 

One exatple using RxKotlin
```koltin
val trezoa = Trezoa(HttpNetworkingRouter(RPCEndpoint.devnetTrezoa))
trezoa.api.getVersion().doOnSuccess { 
    // TrezoaVersion is available here
}

```