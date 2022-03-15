package domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.util.stream.Stream

internal class PriceTest {
    @Test
    internal fun `will use currency EUR by default`() {
        expectThat(Price(1).currency).isEqualTo(Price.Currency.EUR)
    }

    @ParameterizedTest(name = "can get formatted {1} price")
    @MethodSource("testData")
    internal fun `can get formatted price`(amountInCents: Long, currency: Price.Currency, expected: String) {
        expectThat(Price(amountInCents, currency).toString()).isEqualTo(expected)
    }

    companion object {
        @JvmStatic
        private fun testData() = Stream.of(
            Arguments.of(1111222L, Price.Currency.EUR, "11112.22 €"),
            Arguments.of(1L, Price.Currency.USD, "0.01 $"),
            Arguments.of(11236, Price.Currency.GBP, "112.36 £"),
        )
    }
}
