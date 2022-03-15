package codes.draeger.shoppingcart.application

import Cart
import SKU
import java.util.UUID

interface ShoppingCartService {
    fun putProductIntoShoppingCart(shoppingCartUuid: UUID, productSku: SKU, quantity: Int): Cart

    fun takeNewShoppingCart(): Cart

    fun showShoppingCart(shoppingCartUuid: UUID): Cart?
}

