package codes.draeger.adapter

import codes.draeger.model.Cart
import codes.draeger.model.CartItem
import codes.draeger.port.ShoppingCartRepository
import codes.draeger.port.ShoppingCartService
import java.util.UUID

class DomainShoppingCartService(
    private val shoppingCartRepository: ShoppingCartRepository
) : ShoppingCartService {
    override fun putProductIntoShoppingCart(shoppingCartUuid: UUID, item: CartItem): Cart {
        val cart = shoppingCartRepository.findById(shoppingCartUuid) ?: throw CartNotFoundException("$shoppingCartUuid")
        return cart.addItem(item).also {
            shoppingCartRepository.save(it)
        }
    }

    override fun takeNewShoppingCart(): Cart {
        return Cart(
            id = UUID.randomUUID(),
            items = emptySet()
        )
    }

    override fun showShoppingCart(shoppingCartUuid: UUID): Cart? {
        return shoppingCartRepository.findById(shoppingCartUuid)
    }
}

class CartNotFoundException(id: String) : Exception("cart with id '$id' not found")
