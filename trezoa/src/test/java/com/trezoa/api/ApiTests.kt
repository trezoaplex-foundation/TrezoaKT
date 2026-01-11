@file:OptIn(ExperimentalCoroutinesApi::class)

package com.trezoa.api
import com.trezoa.Trezoa
import com.trezoa.TrezoaTestsUtils
import com.trezoa.core.HotAccount
import com.trezoa.core.PublicKey
import com.trezoa.generateTrezoaConnection
import com.trezoa.models.ProgramAccountConfig
import com.trezoa.models.SignatureStatusRequestConfiguration
import com.trezoa.models.buffer.AccountInfoData
import com.trezoa.networking.*
import com.trezoa.networking.serialization.serializers.base64.BorshAsBase64JsonArraySerializer
import com.trezoa.programs.TokenProgram
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.serializer
import org.junit.Assert
import org.junit.Test
import java.lang.Error

class ApiTests {

    val trezoa: Trezoa get() = TrezoaTestsUtils.generateTrezoaConnection()

    @Test
    fun TestGetRecentBlockhash() = runTest {
        val result = trezoa.api.getRecentBlockhash().getOrThrow()
        Assert.assertNotNull(result)
    }

    /*@Test
    fun TestGetBlock() = runTest {
        val result = trezoa.api.getBlock(196291575).getOrThrow()
        Assert.assertNotNull(result)
    }*/

    @Test
    fun TestGetBlockCommitment() = runTest {
        val result = trezoa.api.getBlockCommitment(82493733).getOrThrow()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetBlockHeight() = runTest {
        val result = trezoa.api.getBlockHeight().getOrThrow()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetBlockTime() = runTest {
        val height = trezoa.api.getBlockHeight().getOrThrow()
        val result = trezoa.api.getBlockTime(height-1)
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetClusterNodes() = runTest {
        val result = trezoa.api.getClusterNodes().getOrThrow()
        Assert.assertNotNull(result)
    }

    /*@Test
    fun TestGetConfirmedBlock() = runTest {
//        val slot = trezoa.api.getSnapshotSlot().getOrThrow()
        val slot = 196291575L // Using fixed slot to make sure it doesn't contains unsupported transaction versions.
        val result = trezoa.api.getConfirmedBlock(slot).getOrThrow()
        Assert.assertNotNull(result)
    }*/

    @Test
    fun TestSnapshotSlotBlock() = runTest {
        val result = trezoa.api.getSnapshotSlot().getOrThrow()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetConfirmedBlocks() = runTest {
        val height = trezoa.api.getBlockHeight().getOrThrow()
        val result = trezoa.api.getConfirmedBlocks(height, height - 10).getOrThrow()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetConfirmedSignaturesForAddress2() = runTest {
         val result = trezoa.api.getConfirmedSignaturesForAddress2(PublicKey("5Zzguz4NsSRFxGkHfM4FmsFpGZiCDtY72zH2jzMcqkJx"), 10).getOrThrow()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetEpochInfo() = runTest {
        val result = trezoa.api.getEpochInfo().getOrThrow()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetEpochSchedule() = runTest {
        val result = trezoa.api.getEpochSchedule().getOrThrow()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetFeeCalculatorForBlockhash() = runTest {
        val trezoa = Trezoa(HttpNetworkingRouter(RPCEndpoint.devnetTrezoa))
        val blockhash = trezoa.api.getRecentBlockhash().getOrThrow()
        val result = trezoa.api.getFeeCalculatorForBlockhash(blockhash).getOrThrow()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetFeeRateGovernor() = runTest {
        val result = trezoa.api.getFeeRateGovernor().getOrThrow()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetFees() = runTest {
        val result = trezoa.api.getFees().getOrThrow()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetFirstAvailableBlock() = runTest {
        val result = trezoa.api.getFirstAvailableBlock().getOrThrow()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetGenesisHash() = runTest {
        val result = trezoa.api.getGenesisHash().getOrThrow()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetIdentity() = runTest {
        val result = trezoa.api.getIdentity().getOrThrow()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetInflationRewardNullable() = runTest {
        val result = trezoa.api.getInflationReward(listOf(PublicKey("5U3bH5b6XtG99aVWLqwVzYPVpQiFHytBD68Rz2eFPZd7")))
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetMaxRetransmitSlot() = runTest {
        val result = trezoa.api.getMaxRetransmitSlot()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetMaxShredInsertSlot() = runTest {
        val result = trezoa.api.getMaxShredInsertSlot()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetMinimumBalanceForRentExemption() = runTest {
        val result = trezoa.api.getMinimumBalanceForRentExemption(32000)
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetSlot() = runTest {
        val result = trezoa.api.getSlot()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetSlotLeader() = runTest {
        val result = trezoa.api.getSlotLeader()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetSlotLeaders() = runTest {
        val slot = trezoa.api.getSlot().getOrThrow()
        val result = trezoa.api.getSlotLeaders(slot, 10)
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetSupply() = runTest {
        val result = trezoa.api.getSupply()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetTokenAccountBalance() = runTest {
        val result = trezoa.api.getTokenAccountBalance(PublicKey("FzhfekYF625gqAemjNZxjgTZGwfJpavMZpXCLFdypRFD"))
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetTokenSupply() = runTest {
        val result = trezoa.api.getTokenSupply(PublicKey("2tWC4JAdL4AxEFJySziYJfsAnW2MHKRo98vbAPiRDSk8"))
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetTransactionCount() = runTest {
        val result = trezoa.api.getTransactionCount()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetVersion() = runTest {
        val result = trezoa.api.getVersion()
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetVoteAccounts() = runTest {
        val result = trezoa.api.getVoteAccounts()
        Assert.assertNotNull(result)
    }

    /*@Test
    fun TestRequestAirdrop() = runTest {
        val result = trezoa.api.requestAirdrop(PublicKey("AaXs7cLGcSVAsEt8QxstVrqhLhYN2iGhFNRemwYnHitV"), 1010)
        Assert.assertNotNull(result)
    }*/

    @Test
    fun TestMinimumLedgerSlot() = runTest {
        val result = trezoa.api.minimumLedgerSlot()
        Assert.assertNotNull(result)
    }

    /*@Test
    fun TestGetSignatureStatuses() = runTest {
        val result = trezoa.api.getSignatureStatuses(listOf("3citcRRbx1vTjXazYLXZ4cwVHNkx6baFrSNp5msR2mgTRuuod4qhqTi921emn2CjU93sSM5dGGhCcHeVtvQyPfCV"), SignatureStatusRequestConfiguration(true))
        Assert.assertNotNull(result)
    }*/

    @Test
    fun TestGetStakeActivation() = runTest {
        val result = trezoa.api.getStakeActivation(PublicKey("HDDhNo3H2t3XbLmRswHdTu5L8SvSMypz9UVFu68Wgmaf"))
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetSplTokenAccountInfo() = runTest {
        val mainnetTrezoa = Trezoa(HttpNetworkingRouter(RPCEndpoint.mainnetBetaTrezoa))
        val result = mainnetTrezoa.api.getSplTokenAccountInfo(PublicKey("D3PSQUMEYyDWvNxaPrAhv2ZxMcrCMRqTUD5LHm4HLrAR"))
        Assert.assertNotNull(result)
    }

    @Test
    fun TestGetAccountInfoData() = runTest {
        val account = trezoa.api.getAccountInfo(AccountInfoSerializer(BorshAsBase64JsonArraySerializer((AccountInfoData.serializer()))),
            PublicKey("8hoBQbSFKfDK3Mo7Wwc15Pp2bbkYuJE8TdQmnHNDjXoQ"),
        ).getOrThrow()
        Assert.assertNotNull(account)
        Assert.assertEquals(account!!.owner, TokenProgram.PROGRAM_ID.toBase58())
        Assert.assertEquals(PublicKey("5Zzguz4NsSRFxGkHfM4FmsFpGZiCDtY72zH2jzMcqkJx"), account.data!!.owner, )
    }

    @Test
    fun TestSimulateTransaction() = runTest {
        val transaction =
            "ASdDdWBaKXVRA+6flVFiZokic9gK0+r1JWgwGg/GJAkLSreYrGF4rbTCXNJvyut6K6hupJtm72GztLbWNmRF1Q4BAAEDBhrZ0FOHFUhTft4+JhhJo9+3/QL6vHWyI8jkatuFPQzrerzQ2HXrwm2hsYGjM5s+8qMWlbt6vbxngnO8rc3lqgAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAy+KIwZmU8DLmYglP3bPzrlpDaKkGu6VIJJwTOYQmRfUBAgIAAQwCAAAAuAsAAAAAAAA="
        val addresses = listOf(PublicKey.valueOf("QqCCvshxtqMAL2CVALqiJB7uEeE5mjSPsseQdDzsRUo"))
        val simulatedTransaction: SimulateTransactionValue =
            trezoa.api.simulateTransaction(transaction, addresses).getOrThrow()
        Assert.assertTrue(simulatedTransaction.logs.isNotEmpty())
    }

    //region getProgramAccounts
    @Test
    fun testGetProgramAccountsReturnsValidAccountInfo() = runTest {
        // given
        val account = "accountAddress"
        val request = ProgramAccountRequest(account)
        val expectedAccounts = listOf(AccountInfoWithPublicKey(
            AccountInfo("programAccount", false, 0, "", 0),
            account
        ))

        val trezoaDriver = Api(MockRpcDriver().apply {
            willReturn(request, expectedAccounts)
        })

        // when
        val actualAccounts = trezoaDriver
            .getProgramAccounts(String.serializer(), PublicKey(account), ProgramAccountConfig())
            .getOrDefault(listOf())

        // then
        Assert.assertEquals(expectedAccounts, actualAccounts)
    }

    @Test
    fun testGetProgramAccountsReturnsEmptyListForUnknownAccount() = runTest {
        // given
        val account = "accountAddress"
        val expectedAccounts = listOf<AccountInfoWithPublicKey<String>>()
        val trezoaDriver = Api(MockRpcDriver())

        // when
        val actualAccounts = trezoaDriver
            .getProgramAccounts(String.serializer(), PublicKey(account), ProgramAccountConfig())
            .getOrNull()

        // then
        Assert.assertEquals(expectedAccounts, actualAccounts)
    }

    @Test
    fun testGetProgramAccountsReturnsErrorForInvalidParams() = runTest {
        // given
        val account = "accountAddress"
        val expectedErrorMessage = "Error Message"
        val expectedResult = Result.failure<String>(Error(expectedErrorMessage))
        val trezoaDriver = Api(MockRpcDriver().apply {
            willError(ProgramAccountRequest(account), RpcError(1234, expectedErrorMessage))
        })

        // when
        val actualResult = trezoaDriver.getProgramAccounts(
            String.serializer(), PublicKey(account), ProgramAccountConfig()
        )

        // then
        Assert.assertEquals(expectedResult.isFailure, actualResult.isFailure)
        Assert.assertEquals(expectedErrorMessage, actualResult.exceptionOrNull()?.message)
    }
    //endregion

    //region getMultipleAccountsInfo
    @Test
    fun testGetMultipleAccountsInfoReturnsValidAccountInfo() = runTest {
        // given
        val accounts = listOf(HotAccount().publicKey)
        val accountsRequest = MultipleAccountsRequest(accounts.map { it.toBase58() })
        val expectedAccountInfo = listOf(AccountInfo("testAccount", false, 0, "", 0))
        val trezoaDriver = Api(MockRpcDriver().apply {
            willReturn(accountsRequest, expectedAccountInfo)
        })

        // when
        val actualAccountInfo = trezoaDriver.getMultipleAccountsInfo<String>(serializer(), accounts).getOrNull()

        // then
        Assert.assertEquals(expectedAccountInfo, actualAccountInfo)
    }

    @Test
    fun testGetMultipleAccountsInfoReturnsEmptyListForNullAccount() = runTest {
        // given
        val accounts = listOf(HotAccount().publicKey)
        val expectedAccountInfo = listOf<String>()
        val trezoaDriver = Api(MockRpcDriver())

        // when
        val actualAccountInfo = trezoaDriver.getMultipleAccountsInfo<String>(serializer(), accounts).getOrNull()

        // then
        Assert.assertEquals(expectedAccountInfo, actualAccountInfo)
    }
    //endregion
}
