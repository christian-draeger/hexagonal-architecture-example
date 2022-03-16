package codes.draeger

import codes.draeger.model.Cart
import codes.draeger.model.CartItem
import codes.draeger.model.SKU
import org.springframework.stereotype.Service
import codes.draeger.port.ShoppingCartService
import java.util.UUID

// using driven ports here to compose use-cases
@Service
class AppShoppingCart(
    // use repositories here
): ShoppingCartService {
    override fun putProductIntoShoppingCart(shoppingCartUuid: UUID, item: CartItem): Cart {
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
