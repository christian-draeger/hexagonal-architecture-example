package codes.draeger.shoppingcart.ports.driver.rest

import domain.Cart
import codes.draeger.shoppingcart.application.ShoppingCartService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class ShoppingCartController(
    private val shoppingCartService: ShoppingCartService
) {
    @GetMapping("/info/{id}")
    fun getShoppingCartInfo(@PathVariable id: UUID): ResponseEntity<ShoppingCartInfoDTO> =
        shoppingCartService.showShoppingCart(id)?.let {
            ResponseEntity.ok(it.toShoppingCartInfoDTO())
        } ?: ResponseEntity.notFound().build()
}

fun Cart.toShoppingCartInfoDTO() = ShoppingCartInfoDTO(
    totalPrice = totalPrice.toString(),
    itemCount = items.size,
    totalQuantity = totalQuantity
)

data class ShoppingCartInfoDTO(
    val totalPrice: String,
    val itemCount: Int,
    val totalQuantity: Int,
)
