package codes.draeger.config

import codes.draeger.logger
import codes.draeger.persistence.ShoppingCartEntity
import codes.draeger.persistence.SpringDataMongoRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.util.UUID

@Component
@Profile("testdata")
class TestDataConfig(
    private val springDataMongoRepository: SpringDataMongoRepository
) {

    @EventListener
    @Order(Ordered.LOWEST_PRECEDENCE)
    fun initTestData(event: ApplicationReadyEvent) {
        logger.info("clear all test data")
        springDataMongoRepository.deleteAll()
        logger.info("insert fresh test data")
        springDataMongoRepository.save(testData())
        springDataMongoRepository.save(testData())
    }
}

private fun testData() = ShoppingCartEntity(
    cartId = UUID.randomUUID().toString(),
    items = listOf(
        aShoppingCartEntityItem("12345678", 3),
        aShoppingCartEntityItem("1234567890"),
        aShoppingCartEntityItem("AAABBBCCC"),
        aShoppingCartEntityItem("NOTEXISTING"),
    )
)

private fun aShoppingCartEntityItem(
    sku: String,
    quantity: Int = 1,
) =
    ShoppingCartEntity.Item(
        sku = sku,
        quantity = quantity,
    )
