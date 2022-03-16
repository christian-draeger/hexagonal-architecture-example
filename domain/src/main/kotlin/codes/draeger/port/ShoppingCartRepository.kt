package codes.draeger.port

import codes.draeger.model.Cart
import java.util.UUID

interface ShoppingCartRepository {
    fun findById(id: UUID): Cart?

    fun save(cart: Cart)
}
