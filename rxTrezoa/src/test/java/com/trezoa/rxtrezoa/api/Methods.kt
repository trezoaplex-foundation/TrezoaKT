package com.trezoa.rxtrezoa.api

import com.trezoa.Trezoa
import com.trezoa.api.TrezoaAccountSerializer
import com.trezoa.core.HotAccount
import com.trezoa.core.PublicKey
import com.trezoa.models.SignatureStatusRequestConfiguration
import com.trezoa.models.buffer.AccountInfoData
import com.trezoa.networking.HttpNetworkingRouter
import com.trezoa.networking.RPCEndpoint
import com.trezoa.rxtrezoa.TrezoatestsUtils
import com.trezoa.rxtrezoa.generateTrezoaConnection
import org.junit.Assert
import org.junit.Test
import kotlin.collections.listOf

class Methods {

    val trezoa: Trezoa get() = TrezoatestsUtils.generateTrezoaConnection()

    @Test
    fun TestGetRecentBlockhash() {
        val result = trezoa.api.getRecentBlockhash().blockingGet()
        Assert.assertNotNull(result)
    }
    @Test
    fun TestGetBalance() {
        val result = trezoa.api.getBalance(PublicKey("AaXs7cLGcSVAsEt8QxstVrqhLhYN2iGhFNRemwYnHitV")).blockingGet()
        Assert.assertTrue(result > 0)
    }

    /*@Test
    fun TestGetConfirmedTransaction() {
        val trezoa = Trezoa(OkHttpNetworkingRouter(RPCEndpoint.devnetTrezoa))

        val slot = trezoa.api.getSnapshotSlot().blockingGet()
        val block = trezoa.api.getConfirmedBlock(slot.toInt()).blockingGet()
        val signature = block.transactions!!.first().transaction!!.signatures.first()
        val result = trezoa.api.getConfirmedTransaction(signature).blockingGet()
        Assert.assertTrue(result.slot!! > 0)
        Assert.assertEquals(result.transaction!!.signatures[0], signature)
    }*/

    @Test
    fun TestGetVoteAccounts() {
        val result = trezoa.api.getVoteAccounts().blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetStakeActivation() {
        val result = trezoa.api.getStakeActivation(PublicKey("HDDhNo3H2t3XbLmRswHdTu5L8SvSMypz9UVFu68Wgmaf")).blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetStakeActivationEpoch() {
        val result = trezoa.api.getStakeActivation(PublicKey("HDDhNo3H2t3XbLmRswHdTu5L8SvSMypz9UVFu68Wgmaf"), 143).blockingGet()
        Assert.assertNotNull(result)
    }

   /*@Test
    fun TestRequestAirdrop() {
        val result = trezoa.api.requestAirdrop(HotAccount().publicKey, 1000000000).blockingGet()
        Assert.assertNotNull(result)
    }*/

    @Test
    fun TestGetMinimumBalanceForRentExemption() {
        val result = trezoa.api.getMinimumBalanceForRentExemption(32000).blockingGet()
        Assert.assertNotNull(result)
    }

    /*@Test
    fun TestGetBlockTime() {
        val trezoa = Trezoa(HttpNetworkingRouter(RPCEndpoint.mainnetBetaTrezoa))
        val height = trezoa.api.getBlockHeight().blockingGet()
        val result = trezoa.api.getBlockTime(height-1).blockingGet()
        Assert.assertNotNull(result)
    }*/

    @Test
    fun TestGetAccountInfo() {
        val result = trezoa.api.getAccountInfo(TrezoaAccountSerializer(AccountInfoData.serializer()), PublicKey("AaXs7cLGcSVAsEt8QxstVrqhLhYN2iGhFNRemwYnHitV")).blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetBlockHeight() {
        val result = trezoa.api.getBlockHeight().blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetVersion() {
        val result = trezoa.api.getVersion().blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestMinimumLedgerSlot() {
        val result = trezoa.api.minimumLedgerSlot().blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetFeeCalculatorForBlockhash() {
        val blockhash = trezoa.api.getRecentBlockhash().blockingGet()
        val result = trezoa.api.getFeeCalculatorForBlockhash(blockhash).blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetBlockCommitment() {
        val result = trezoa.api.getBlockCommitment(82493733).blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetFeeRateGovernor() {
        val result = trezoa.api.getFeeRateGovernor().blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetFees() {
        val result = trezoa.api.getFees().blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetTransactionCount() {
        val result = trezoa.api.getTransactionCount().blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetMaxRetransmitSlot() {
        val result = trezoa.api.getMaxRetransmitSlot().blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetSupply() {
        val result = trezoa.api.getSupply().blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetFirstAvailableBlock() {
        val result = trezoa.api.getFirstAvailableBlock().blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetGenesisHash() {
        val result = trezoa.api.getGenesisHash().blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetEpochInfo() {
        val result = trezoa.api.getEpochInfo().blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetEpochSchedule() {
        val result = trezoa.api.getEpochSchedule().blockingGet()
        Assert.assertNotNull(result)
    }

    /*@Test
    fun TestGetConfirmedBlock() {
        val slot = 196288837L // Using fixed slot to make sure it doesn't contains unsupported transaction versions.
        val result = trezoa.api.getConfirmedBlock(slot.toInt()).blockingGet()
        Assert.assertNotNull(result)
    }*/

    @Test
    fun TestGetSnapshotSlot() {
        val result = trezoa.api.getSnapshotSlot().blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetMaxShredInsertSlot() {
        val result = trezoa.api.getMaxShredInsertSlot().blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetSlot() {
        val result = trezoa.api.getSlot().blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetConfirmedBlocks() {
        val height = trezoa.api.getBlockHeight().blockingGet().toInt()
        val result = trezoa.api.getConfirmedBlocks(height, height - 10).blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetSplTokenAccountInfo() {
        val trezoa = Trezoa(HttpNetworkingRouter(RPCEndpoint.mainnetBetaTrezoa))
        val result = trezoa.api.getSplTokenAccountInfo(PublicKey("D3PSQUMEYyDWvNxaPrAhv2ZxMcrCMRqTUD5LHm4HLrAR")).blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetSlotLeader() {
        val result = trezoa.api.getSlotLeader().blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetClusterNodes() {
        val result = trezoa.api.getClusterNodes().blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetTokenAccountBalance() {
        val result = trezoa.api.getTokenAccountBalance(PublicKey("FzhfekYF625gqAemjNZxjgTZGwfJpavMZpXCLFdypRFD")).blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetConfirmedSignaturesForAddress2() {
        val result = trezoa.api.getConfirmedSignaturesForAddress2(PublicKey("5Zzguz4NsSRFxGkHfM4FmsFpGZiCDtY72zH2jzMcqkJx"), 10, null, null).blockingGet()
        Assert.assertNotNull(result)
    }

    /*@Test
    fun TestGetSignatureStatuses() {
        val result = trezoa.api.getSignatureStatuses(listOf("3citcRRbx1vTjXazYLXZ4cwVHNkx6baFrSNp5msR2mgTRuuod4qhqTi921emn2CjU93sSM5dGGhCcHeVtvQyPfCV"), SignatureStatusRequestConfiguration(true)).blockingGet()
        Assert.assertNotNull(result)
    }*/

    @Test
    fun TestGetTokenSupply() {
        val result = trezoa.api.getTokenSupply(PublicKey("2tWC4JAdL4AxEFJySziYJfsAnW2MHKRo98vbAPiRDSk8")).blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetTokenLargestAccounts() {
        val result = trezoa.api.getTokenLargestAccounts(PublicKey("2tWC4JAdL4AxEFJySziYJfsAnW2MHKRo98vbAPiRDSk8")).blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetSlotLeaders() {
        val slot = trezoa.api.getSlot().blockingGet()
        val result = trezoa.api.getSlotLeaders(slot, 10).blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetIdentity() {
        val result = trezoa.api.getIdentity().blockingGet()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetInflationReward() {
        val result = trezoa.api.getInflationReward(listOf(PublicKey("5U3bH5b6XtG99aVWLqwVzYPVpQiFHytBD68Rz2eFPZd7"))).blockingGet()
        Assert.assertNotNull(result)
    }

    /*
    TODO: Fix when  data is null
    @Test
    fun TestGetProgramAccounts() {
        val trezoa = Trezoa(NetworkingRouter(RPCEndpoint.devnetTrezoa))
        val result = trezoa.api.getProgramAccounts(PublicKey("SwaPpA9LAaLfeLi3a68M4DjnLqgtticKg6CnyNwgAC8"), TokenSwapInfo::class.java).blockingGet()
        Assert.assertNotNull(result)
    }
    */


    /*@Test
    fun TestGetBlock() {
        val slot = trezoa.api.getSlot().blockingGet()
        val result = trezoa.api.getBlock(slot.toInt()).blockingGet()
        Assert.assertNotNull(result)
    }*/
}