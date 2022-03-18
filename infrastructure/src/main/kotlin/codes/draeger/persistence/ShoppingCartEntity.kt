package codes.draeger.persistence

import codes.draeger.model.Cart
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.UUID

@Document(collection = "shopping-cart")
data class ShoppingCartEntity(
    @Id
    val id: ObjectId = ObjectId.get(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Indexed(unique = true)
    val cartId: String,
    val items: List<Item>
) {
    data class Item(
        val quantity: Int,
        val sku: String,
    )

    fun toCart() = Cart(
        id = UUID.fromString(cartId),
        items = emptySet()
    )
}

fun Cart.toShoppingCartEntity() = ShoppingCartEntity(
    cartId = id.toString(),
    items = items.map {
        ShoppingCartEntity.Item(
            quantity = it.quantity,
            sku = it.product.sku.toString()
        )
    }
)
