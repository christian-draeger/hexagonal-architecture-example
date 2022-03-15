package codes.draeger.shoppingcart.application

import Cart
import SKU
import org.springframework.stereotype.Service
import java.util.UUID

// using driven ports here to compose use-cases
@Service
class AppShoppingCart(
    // use repositories here
): ShoppingCartService {
    override fun putProductIntoShoppingCart(shoppingCartUuid: UUID, productSku: SKU, quantity: Int): Cart {
        // TODO: implement
        return Cart(UUID.randomUUID(), emptySet())
    }

    override fun takeNewShoppingCart(): Cart {
        // TODO: implement
        return Cart(UUID.randomUUID(), emptySet())
    }

    override fun showShoppingCart(shoppingCartUuid: UUID): Cart {
        // TODO: implement
        return Cart(UUID.randomUUID(), emptySet())
    }
}
