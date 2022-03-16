package codes.draeger.port

import codes.draeger.model.Cart
import codes.draeger.model.CartItem
import java.util.UUID

interface ShoppingCartService {
    fun putProductIntoShoppingCart(shoppingCartUuid: UUID, item: CartItem): Cart

    fun takeNewShoppingCart(): Cart

    fun showShoppingCart(shoppingCartUuid: UUID): Cart?
}
