package codes.draeger.port

import codes.draeger.model.Cart
import codes.draeger.model.CartItem
import codes.draeger.model.SKU
import java.util.UUID

interface ShoppingCartService {
    fun putProductIntoShoppingCart(shoppingCartUuid: UUID, item: CartItem): Cart

    fun removeProductFromShoppingCart(shoppingCartUuid: UUID, sku: SKU): Cart

    fun takeNewShoppingCart(): Cart

    fun showShoppingCart(shoppingCartUuid: UUID): Cart?

    fun giveBackShoppingCart(shoppingCartUuid: UUID)
}
