package codes.draeger.service

import codes.draeger.model.Cart
import codes.draeger.model.CartItem
import codes.draeger.model.SKU
import codes.draeger.port.ShoppingCartRepository
import codes.draeger.port.ShoppingCartService
import org.springframework.stereotype.Service
import java.util.UUID

class ShoppingCartServicePort(
    private val shoppingCartRepository: ShoppingCartRepository
) : ShoppingCartService {
    override fun putProductIntoShoppingCart(shoppingCartUuid: UUID, item: CartItem): Cart {
        val cart = shoppingCartRepository.findById(shoppingCartUuid) ?: throw CartNotFoundException("$shoppingCartUuid")
        return cart.addItem(item).also {
            shoppingCartRepository.delete(it.id)
            shoppingCartRepository.save(it)
        }
    }

    override fun removeProductFromShoppingCart(shoppingCartUuid: UUID, sku: SKU): Cart {
        TODO("Not yet implemented")
    }

    override fun takeNewShoppingCart(): Cart {
        return Cart(
            id = UUID.randomUUID(),
            items = emptySet()
        ).also(shoppingCartRepository::save)
    }

    override fun showShoppingCart(shoppingCartUuid: UUID): Cart? =
        shoppingCartRepository.findById(shoppingCartUuid)

    override fun giveBackShoppingCart(shoppingCartUuid: UUID) {
        shoppingCartRepository.delete(shoppingCartUuid)
    }
}

class CartNotFoundException(id: String) : Exception("cart with id '$id' not found")
