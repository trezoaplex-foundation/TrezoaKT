package com.trezoa.programs

import com.trezoa.core.AccountMeta
import com.trezoa.core.PublicKey
import com.trezoa.core.TransactionInstruction

/**
 * Abstract class for
 */
abstract class Program {
    companion object {
        /**
         * Returns a [TransactionInstruction] built from the specified values.
         * @param programId Trezoa program we are calling
         * @param keys AccountMeta keys
         * @param data byte array sent to Trezoa
         * @return [TransactionInstruction] object containing specified values
         */
        @JvmStatic
        fun createTransactionInstruction(
            programId: PublicKey,
            keys: List<AccountMeta>,
            data: ByteArray
        ): TransactionInstruction {
            return TransactionInstruction(programId, keys, data)
        }
    }
}