package codes.draeger.adapter

import aValidCart
import aValidCartItem
import anEmptyCart
import codes.draeger.port.ShoppingCartRepository
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo
import strikt.assertions.isNull
import java.util.UUID

internal class DomainShoppingCartServiceTest {

    private val shoppingCartRepository: ShoppingCartRepository = mockk()
    private val domainShoppingCartService = DomainShoppingCartService(shoppingCartRepository)

    @Test
    fun `can put product into shopping cart`() {
        val itemToAdd = aValidCartItem()
        val cartId = UUID.randomUUID()

        every { shoppingCartRepository.save(any()) } just Runs
        every { shoppingCartRepository.findById(any()) } returns anEmptyCart(id = cartId)

        val cart = domainShoppingCartService.putProductIntoShoppingCart(
            UUID.randomUUID(),
            itemToAdd
        )

        expectThat(cart.id).isEqualTo(cartId)
        expectThat(cart.items).containsExactly(itemToAdd)
    }

    @Test
    fun `can take new shopping cart`() {
        expectThat(domainShoppingCartService.takeNewShoppingCart())
            .get { items }.isEmpty()
    }

    @Test
    fun `can show shopping cart`() {
        val existingCart = aValidCart()
        every { shoppingCartRepository.findById(any()) } returns existingCart
        val cart = domainShoppingCartService.showShoppingCart(existingCart.id)
        expectThat(cart).isEqualTo(existingCart)
    }

@Test
    fun `will return null if shopping cart can not be found`() {
        every { shoppingCartRepository.findById(any()) } returns null
        val cart = domainShoppingCartService.showShoppingCart(UUID.randomUUID())
        expectThat(cart).isNull()
    }
}
