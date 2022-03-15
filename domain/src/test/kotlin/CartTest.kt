import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly

internal class CartTest {

    @Test
    internal fun `can add item to EMPTY cart`() {
        val anItem = aValidCartItem(quantity = 3)
        with(anEmptyCart()) {
            expectThat(addItem(anItem)) {
                get { items }.containsExactly(anItem)
            }
        }
    }

    @Test
    internal fun `can add item to cart that already include OTHER products`() {
        val anItem = aValidCartItem(
            product = aValidProduct(price = 1337.toPrice()),
            quantity = 2
        )

        val anotherItem = aValidCartItem(
            product = aValidProduct(price = 1337.toPrice()),
            quantity = 1
        )

        val cart = aValidCart(items = setOf(anItem))

        expectThat(cart.addItem(anotherItem)) {
            get { items }.containsExactly(anItem, anotherItem)
        }
    }

    @Test
    internal fun `can add item to cart that already include SAME products`() {
        val anItem = aValidCartItem(quantity = 3)
        val cart = aValidCart(items = setOf(anItem))

        expectThat(cart.addItem(anItem)) {
            get { items }.containsExactly(anItem.copy(quantity = 6))
        }
    }

    @Test
    internal fun `can REMOVE existing item from cart`() {
        val anItem = aValidCartItem()
        val anotherItem = aValidCartItem(
            product = aValidProduct(
                sku = SKU(aValidSKU_12chars),
                name = Name("Sony PS5")
            )
        )

        val cart = anEmptyCart(items = setOf(anItem, anotherItem))

        expectThat(cart.removeItem(anotherItem)) {
            get { items }.containsExactly(anItem)
        }
    }
}
