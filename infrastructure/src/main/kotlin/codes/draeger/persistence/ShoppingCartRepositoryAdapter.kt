package codes.draeger.persistence

import codes.draeger.metrics.CustomMeters
import codes.draeger.model.Cart
import codes.draeger.port.ShoppingCartRepository
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class ShoppingCartRepositoryAdapter(
    private val springDataMongoRepository: SpringDataMongoRepository,
    private val customMeters: CustomMeters,
) : ShoppingCartRepository {
    override fun findById(id: UUID): Cart? {
        val carts = springDataMongoRepository.findByCartId("$id")
        if (carts.isEmpty()) return null
        return carts.first().toCart()
    }

    override fun save(cart: Cart) {
        customMeters.incrementShoppingCartSaved(cart.id.toString())
        springDataMongoRepository.save(cart.toShoppingCartEntity())
    }

    override fun delete(id: UUID) {
        val carts = springDataMongoRepository.findByCartId("$id")
        if (carts.isNotEmpty()) {
            springDataMongoRepository.delete(carts.first())
        }
    }
}

interface SpringDataMongoRepository : MongoRepository<ShoppingCartEntity, ObjectId> {
    fun findByCartId(cartId: String): List<ShoppingCartEntity>
}
