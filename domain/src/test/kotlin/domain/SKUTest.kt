package domain

import aValidSKU_10chars
import aValidSKU_12chars
import aValidSKU_8chars
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import strikt.assertions.isTrue

internal class SKUTest {

    @ParameterizedTest(name = "can create a valid domain.SKU of value '{0}'")
    @ValueSource(
        strings = [
            aValidSKU_8chars,
            aValidSKU_10chars,
            aValidSKU_12chars,
        ]
    )
    fun `can get SKU value`(value: String) {
        expectThat(SKU(value).value).isEqualTo(value)
    }

    @ParameterizedTest(name = "will throw exception on invalid domain.SKU of value '{0}'")
    @ValueSource(
        strings = [
            "1234567",
            "1234567890123",
            "1234ab567x",
        ]
    )
    fun `will throw exception on invalid SKU`(value: String) {
        expectThrows<IllegalArgumentException> {
            SKU(value)
        }.get {
            message
        }.isEqualTo("invalid domain.SKU '$value' given")
    }

    @Test
    internal fun `can create random SKU with default length`() {
        with(SKU.randomSKU()) {
            expectThat(isValid).isTrue()
            expectThat(value.length).isEqualTo(10)
        }
    }

    @ParameterizedTest(name = "can create random domain.SKU with VALID length of {0}")
    @ValueSource(ints = [8, 9, 10, 11, 12])
    internal fun `can create random SKU`(length: Int) {
        expectThat(SKU.randomSKU(length).isValid).isTrue()
    }

    @ParameterizedTest(name = "can NOT create random domain.SKU with INVALID length of {0}")
    @ValueSource(ints = [3, 7, 13, 42])
    internal fun `can NOT create random SKU with invalid length`(length: Int) {
        expectThrows<IllegalArgumentException> {
            SKU.randomSKU(length)
        }
    }
}
