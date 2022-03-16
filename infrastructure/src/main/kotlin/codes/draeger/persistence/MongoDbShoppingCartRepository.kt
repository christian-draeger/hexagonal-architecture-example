package codes.draeger.persistence

import codes.draeger.model.Cart
import codes.draeger.port.ShoppingCartRepository
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class MongoDbShoppingCartRepository(
    private val springDataMongoRepository: SpringDataMongoRepository
) : ShoppingCartRepository {
    override fun findById(id: UUID): Cart? {

        val carts = springDataMongoRepository.findByCartId("$id")
        if (carts.isEmpty()) return null
        return carts.first().toCart()
    }

    override fun save(cart: Cart) {
        TODO("Not yet implemented")
    }
}

data class ShoppingCartEntity(
    @Id
    val id: ObjectId = ObjectId.get(),
    val cartId: String
) {
    fun toCart() = Cart(
        id = UUID.fromString(cartId),
        items = emptySet() // don't care about items for now to simplify showcase
    )
}

interface SpringDataMongoRepository : MongoRepository<ShoppingCartEntity, ObjectId> {
    fun findByCartId(cartId: String): List<ShoppingCartEntity>
}
