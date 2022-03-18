package codes.draeger.api

import codes.draeger.MockMvcIntegrationTestSetup
import codes.draeger.aValidCart
import codes.draeger.anEmptyCart
import codes.draeger.persistence.ShoppingCartEntity
import codes.draeger.persistence.SpringDataMongoRepository
import codes.draeger.persistence.toShoppingCartEntity
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

internal class ShoppingCartControllerIT(
    @Autowired private val repository: SpringDataMongoRepository
) : MockMvcIntegrationTestSetup() {

    @BeforeEach
    internal fun setUp() {
        repository.deleteAll()
    }

    @Test
    internal fun `can take new shopping cart`() {
        mockMvc.get("/api/v1/new")
            .andExpect {
                status { isOk() }
            }.andExpectBody(ShoppingCartResponse::class.java) {
                expectThat(items).isEmpty()
            }
    }

    @Test
    internal fun `will return 404 on unknown cart id`() {
        val cartId = "NOTEXISTING"
        mockMvc.get("/api/v1/$cartId")
            .andExpect {
                status { isBadRequest() }
            }
    }

    @Test
    internal fun `can get info about a certain cart`() {
        with(aValidCart().toShoppingCartEntity()) {
            repository.save(this)

            mockMvc.get("/api/v1/$cartId/info")
                .andExpect {
                    status { isOk() }
                }
        }
    }

    @Test
    internal fun `can get a certain cart`() {
        with(aValidCart().toShoppingCartEntity()) {
            repository.save(this)

            mockMvc.get("/api/v1/$cartId")
                .andExpect {
                    status { isOk() }
                }
        }
    }

    @Test
    internal fun `can add item to cart`() {
        with(anEmptyCart().toShoppingCartEntity()) {
            repository.save(this)

            mockMvc.post("/api/v1/$cartId") {
                withJsonBody(ShoppingCartItemRequest(3, "4711E1337"))
            }.andExpect {
                status { isOk() }
            }
        }
    }
}
