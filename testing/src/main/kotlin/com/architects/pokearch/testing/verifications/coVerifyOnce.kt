package com.architects.pokearch.testing.verifications

import io.mockk.MockK
import io.mockk.MockKDsl
import io.mockk.MockKVerificationScope
import io.mockk.Ordering

fun coVerifyOnce(
    ordering: Ordering = Ordering.UNORDERED,
    inverse: Boolean = false,
    atLeast: Int = 1,
    atMost: Int = Int.MAX_VALUE,
    timeout: Long = 0,
    verifyBlock: suspend MockKVerificationScope.() -> Unit
) = MockK.useImpl {
    MockKDsl.internalCoVerify(
        ordering,
        inverse,
        atLeast,
        atMost,
        1,
        timeout,
        verifyBlock,
    )
}

fun verifyOnce(
    ordering: Ordering = Ordering.UNORDERED,
    inverse: Boolean = false,
    atLeast: Int = 1,
    atMost: Int = Int.MAX_VALUE,
    timeout: Long = 0,
    verifyBlock: MockKVerificationScope.() -> Unit
) = MockK.useImpl {
    MockKDsl.internalVerify(ordering, inverse, atLeast, atMost, 1, timeout, verifyBlock)
}
