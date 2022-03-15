package domain

import aValidCartItem
import aValidProduct
import aValidSKU_10chars
import org.junit.jupiter.api.Test
import strikt.api.expectCatching
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.hasSize
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isFailure

internal class CartItemTest {
    @Test
    internal fun `check cart items are immutable`() {
        with(aValidCartItem(quantity = 5)) {
            increaseQuantity(3)

            expectThat(quantity).isEqualTo(5)
        }
    }

    @Test
    internal fun `can increase cart item`() {
        val copy = with(aValidCartItem(quantity = 6)) {
            increaseQuantity(4)
        }
        expectThat(copy.quantity).isEqualTo(10)
    }

    @Test
    internal fun `can decrease cart item`() {
        val copy = with(aValidCartItem(quantity = 5)) {
            decreaseQuantity(4)
        }
        expectThat(copy.quantity).isEqualTo(1)
    }

    @Test
    internal fun `can NOT decrease cart item below quantity of 1`() {
        expectCatching { aValidCartItem(quantity = 3).decreaseQuantity(3) }
            .isFailure()
            .isA<IllegalArgumentException>()
            .get { message }.isEqualTo("cart item amount needs to be at least 1 but was '0'")
    }

    @Test
    internal fun `can NOT increase cart item over quantity of 10`() {
        expectCatching { aValidCartItem(quantity = 8).increaseQuantity(3) }
            .isFailure()
            .isA<IllegalArgumentException>()
            .get { message }.isEqualTo("cart item amount of '11' exceeded the max amount of 10")
    }

    @Test
    internal fun `can get price of cart item`() {
        val item = aValidCartItem(
            product = aValidProduct(price = Price(inCents = 3333)),
            quantity = 5
        )
        expectThat(item.price).isEqualTo(
            Price(
                inCents = 16665,
                currency = Price.Currency.EUR
            )
        )
    }

    @Test
    internal fun `can merge items with same product sku, while freshly added item will define product data`() {
        val sku = aValidSKU_10chars.toSKU()

        val item = aValidCartItem(
            product = aValidProduct(
                sku = sku,
                name = "domain.Product A".toName(),
                price = 3333.toPrice()
            ),
            quantity = 2
        )

        val itemToMerge = aValidCartItem(
            product = aValidProduct(
                sku = sku,
                name = "domain.Product B".toName(),
                price = 5555.toPrice()
            ),
            quantity = 2
        )
        expectThat(item.merge(itemToMerge)).isEqualTo(itemToMerge.copy(quantity = 4))
    }

    @Test
    internal fun `will ignore items that are null while merging`() {
        val item = aValidCartItem()
        expectThat(item.merge(null)).isEqualTo(item)
    }

    @Test
    internal fun `can add items with each other`() {
        val item = aValidCartItem()
        val anotherItem = aValidCartItem()
        expectThat(item + anotherItem)
            .isA<Set<CartItem>>()
            .containsExactly(item, anotherItem)
    }

    @Test
    internal fun `will merge items with SAME sku when adding each other`() {
        val item = aValidCartItem(
            product = aValidProduct(sku = aValidSKU_10chars.toSKU()),
            quantity = 2
        )
        val anotherItem = aValidCartItem(
            product = aValidProduct(sku = aValidSKU_10chars.toSKU()),
            quantity = 4
        )
        expectThat(item + anotherItem)
            .isA<Set<CartItem>>()
            .hasSize(1)
            .containsExactly(anotherItem.copy(quantity = 6))
    }
}
