package io.mths.swing.flow.optional

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.`should be equal to`
import java.util.Optional
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TakeIfPresentTest {
    @Test
    fun `Drop empty optionals`() = runTest {
        val optionals = flowOf(
            Optional.empty<String>(),
            Optional.of("Single"),
            Optional.empty()
        )
        val presentValueCount =
            optionals.takeIfPresent()
                .toList()
                .size

        presentValueCount `should be equal to` 1
    }
}