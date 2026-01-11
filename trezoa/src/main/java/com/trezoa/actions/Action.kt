package com.trezoa.actions

import com.trezoa.api.Api
import com.trezoa.models.Token
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class Action(
    val api: Api,
    val supportedTokens: List<Token>,
    val dispatcher: CoroutineDispatcher = Dispatchers.IO
)
