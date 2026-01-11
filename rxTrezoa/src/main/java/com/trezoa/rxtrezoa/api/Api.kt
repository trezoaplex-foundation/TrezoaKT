package com.trezoa.rxtrezoa.api

import com.trezoa.api.*
import com.trezoa.api.Block
import com.trezoa.api.ClusterNode
import com.trezoa.api.ConfirmedBlock
import com.trezoa.api.EpochInfo
import com.trezoa.api.EpochSchedule
import com.trezoa.api.FeeCalculatorInfo
import com.trezoa.api.FeeRateGovernorInfo
import com.trezoa.api.FeesInfo
import com.trezoa.api.SignatureInformation
import com.trezoa.api.SignatureStatus
import com.trezoa.api.TrezoaVersion
import com.trezoa.api.SplTokenAccountInfo
import com.trezoa.api.StakeActivation
import com.trezoa.api.Supply
import com.trezoa.api.VoteAccounts
import com.trezoa.core.Account
import com.trezoa.core.PublicKey
import com.trezoa.core.Transaction
import com.trezoa.models.*
import io.reactivex.Single
import io.reactivex.disposables.Disposables
import kotlinx.serialization.KSerializer

fun Api.getRecentBlockhash(): Single<String> {
    return Single.create { emitter ->
         this.getRecentBlockhash { result ->
             result.onSuccess {
                 emitter.onSuccess(it)
             }.onFailure {
                 emitter.onError(it)
             }
         }
        Disposables.empty()
    }
}

fun Api.getBalance(account: PublicKey): Single<Long> {
    return Single.create { emitter ->
        this.getBalance(account) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getConfirmedTransaction(signature: String): Single<ConfirmedTransactionSerializable> {
    return Single.create { emitter ->
        this.getConfirmedTransaction(signature,) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.sendTransaction(transaction: Transaction, signer: List<Account>): Single<String> {
    return Single.create { emitter ->
        this.sendTransaction(transaction, signer) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getVoteAccounts(): Single<VoteAccounts> {
    return Single.create { emitter ->
        this.getVoteAccounts() { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getStakeActivation(publicKey: PublicKey): Single<StakeActivation> {
    return Single.create { emitter ->
        this.getStakeActivation(publicKey) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getStakeActivation(publicKey: PublicKey, epoch: Long): Single<StakeActivation> {
    return Single.create { emitter ->
        this.getStakeActivation(publicKey, epoch) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

inline fun <reified T>Api.getAccountInfo(
    serializer: KSerializer<T>,
    publicKey: PublicKey
): Single<T> {
    return Single.create { emitter ->
        this.getAccountInfo(serializer, publicKey) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.requestAirdrop(publicKey: PublicKey, lamports: Long): Single<String> {
    return Single.create { emitter ->
        this.requestAirdrop(publicKey, lamports) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getMinimumBalanceForRentExemption(dataLength: Long): Single<Long> {
    return Single.create { emitter ->
        this.getMinimumBalanceForRentExemption(dataLength) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getBlockTime(block: Long): Single<Long> {
    return Single.create { emitter ->
        this.getBlockTime(block) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getBlockHeight(): Single<Long> {
    return Single.create { emitter ->
        this.getBlockHeight { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.minimumLedgerSlot(): Single<Long> {
    return Single.create { emitter ->
        this.minimumLedgerSlot { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getVersion(): Single<TrezoaVersion> {
    return Single.create { emitter ->
        this.getVersion { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getFeeCalculatorForBlockhash(blockhash: String): Single<FeeCalculatorInfo> {
    return Single.create { emitter ->
        this.getFeeCalculatorForBlockhash(blockhash) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getBlockCommitment(block: Long): Single<GetBlockCommitmentResponse> {
    return Single.create { emitter ->
        this.getBlockCommitment(block) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getFeeRateGovernor(): Single<FeeRateGovernorInfo> {
    return Single.create { emitter ->
        this.getFeeRateGovernor { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getFees(): Single<FeesInfo> {
    return Single.create { emitter ->
        this.getFees { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getTransactionCount(): Single<Long> {
    return Single.create { emitter ->
        this.getTransactionCount { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getMaxRetransmitSlot(): Single<Long> {
    return Single.create { emitter ->
        this.getMaxRetransmitSlot { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getSupply(): Single<Supply> {
    return Single.create { emitter ->
        this.getSupply { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getFirstAvailableBlock(): Single<Long> {
    return Single.create { emitter ->
        this.getFirstAvailableBlock { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getGenesisHash(): Single<String> {
    return Single.create { emitter ->
        this.getGenesisHash { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getBlock(slot: Int): Single<Block> {
    return Single.create { emitter ->
        this.getBlock(slot) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getEpochInfo(): Single<EpochInfo> {
    return Single.create { emitter ->
        this.getEpochInfo { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getEpochSchedule(): Single<EpochSchedule> {
    return Single.create { emitter ->
        this.getEpochSchedule { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getConfirmedBlock(slot: Int): Single<ConfirmedBlock> {
    return Single.create { emitter ->
        this.getConfirmedBlock(slot) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getSnapshotSlot(): Single<Long> {
    return Single.create { emitter ->
        this.getSnapshotSlot { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getMaxShredInsertSlot(): Single<Long> {
    return Single.create { emitter ->
        this.getMaxShredInsertSlot { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getSlot(): Single<Long> {
    return Single.create { emitter ->
        this.getSlot { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getSignatureStatuses(signatures: List<String>, configs: SignatureStatusRequestConfiguration? = SignatureStatusRequestConfiguration()): Single<List<SignatureStatus>> {
    return Single.create { emitter ->
        this.getSignatureStatuses(signatures, configs) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getConfirmedBlocks(start: Int, end: Int): Single<List<Double>> {
    return Single.create { emitter ->
        this.getConfirmedBlocks(start, end) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getSplTokenAccountInfo(account: PublicKey): Single<SplTokenAccountInfo> {
    return Single.create { emitter ->
        this.getSplTokenAccountInfo(account) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getSlotLeader(): Single<PublicKey> {
    return Single.create { emitter ->
        this.getSlotLeader() { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getClusterNodes(): Single<List<ClusterNode>> {
    return Single.create { emitter ->
        this.getClusterNodes() { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getTokenAccountBalance(tokenMint: PublicKey): Single<TokenAmountInfoResponse> {
    return Single.create { emitter ->
        this.getTokenAccountBalance(tokenMint) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getTokenSupply(tokenMint: PublicKey): Single<TokenAmountInfoResponse> {
    return Single.create { emitter ->
        this.getTokenSupply(tokenMint) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getTokenLargestAccounts(tokenMint: PublicKey): Single<List<TokenAccount>> {
    return Single.create { emitter ->
        this.getTokenLargestAccounts(tokenMint) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getConfirmedSignaturesForAddress2(account: PublicKey,
                                          limit: Int? = null,
                                          before: String? = null,
                                          until: String? = null): Single<List<SignatureInformation>> {
    return Single.create { emitter ->
        this.getConfirmedSignaturesForAddress2(account, limit, before, until) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getInflationReward(addresses: List<PublicKey>): Single<List<InflationRewardResponse>> {
    return Single.create { emitter ->
        this.getInflationReward(addresses) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getIdentity(): Single<PublicKey> {
    return Single.create { emitter ->
        this.getIdentity { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun Api.getSlotLeaders(startSlot: Long,
                       limit: Long): Single<List<PublicKey>> {
    return Single.create { emitter ->
        this.getSlotLeaders(startSlot, limit) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}


fun Api.simulateTransaction(transaction: String,
                            addresses: List<PublicKey>,): Single<SimulateTransactionValue> {
    return Single.create { emitter ->
        this.simulateTransaction(transaction, addresses) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}

fun <T>Api.getProgramAccounts(serializer: KSerializer<T>, address: PublicKey): Single<List<ProgramAccountSerialized<AccountInfo<T>>>> {
    return Single.create { emitter ->
        this.getProgramAccounts(serializer, address) { result ->
            result.onSuccess {
                emitter.onSuccess(it)
            }.onFailure {
                emitter.onError(it)
            }
        }
        Disposables.empty()
    }
}