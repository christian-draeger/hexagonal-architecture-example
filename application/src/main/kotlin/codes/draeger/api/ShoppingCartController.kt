package codes.draeger.api

import codes.draeger.model.Cart
import codes.draeger.model.CartItem
import codes.draeger.model.Name
import codes.draeger.model.Price
import codes.draeger.model.Product
import codes.draeger.model.SKU
import codes.draeger.port.ShoppingCartService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1")
class ShoppingCartController(
    private val shoppingCartService: ShoppingCartService
) {
    @GetMapping("/{id}/info")
    fun getShoppingCartInfo(@PathVariable id: UUID): ResponseEntity<ShoppingCartInfoResponse> =
        shoppingCartService.showShoppingCart(id)?.let {
            ResponseEntity.ok(it.toShoppingCartInfoResponse())
        } ?: ResponseEntity.notFound().build()

    @GetMapping("/{id}")
    fun getShoppingCart(@PathVariable id: UUID): ResponseEntity<ShoppingCartResponse> =
        shoppingCartService.showShoppingCart(id)?.let {
            ResponseEntity.ok(it.toShoppingCartResponse())
        } ?: ResponseEntity.notFound().build()

    @GetMapping("/new")
    fun getNewShoppingCart(): ResponseEntity<ShoppingCartResponse> =
        ResponseEntity.ok(shoppingCartService.takeNewShoppingCart().toShoppingCartResponse())

    @PostMapping("/{id}")
    fun putItemIntoShoppingCart(
        @PathVariable id: UUID,
        @RequestBody item: ShoppingCartItemRequest
    ): ResponseEntity<Unit> {
        // TODO: call product service by sku to retrieve needed product data - map response to CartItem
        val cartItem = CartItem(Product(SKU(item.sku), Price(12345), Name("aaa")), item.quantity)
        shoppingCartService.putProductIntoShoppingCart(id, cartItem)
        return ResponseEntity.ok().build()
    }
}

data class ShoppingCartResponse(
    val id: String,
    val totalPrice: String,
    val totalQuantity: Int,
    val items: List<ShoppingCartItem>
) {
    data class ShoppingCartItem(
        val quantity: Int,
        val sku: String,
        val price: String,
        val name: String
    )
}

fun Cart.toShoppingCartResponse() = ShoppingCartResponse(
    id = id.toString(),
    totalPrice = totalPrice.toString(),
    totalQuantity = totalQuantity,
    items = items.map {
        ShoppingCartResponse.ShoppingCartItem(
            quantity = it.quantity,
            sku = it.product.sku.toString(),
            price = it.price.toString(),
            name = it.product.name.toString(),
        )
    }
)

data class ShoppingCartInfoResponse(
    val totalPrice: String,
    val itemCount: Int,
    val totalQuantity: Int,
)

fun Cart.toShoppingCartInfoResponse() = ShoppingCartInfoResponse(
    totalPrice = totalPrice.toString(),
    itemCount = items.size,
    totalQuantity = totalQuantity,
)

data class ShoppingCartItemRequest(
    val quantity: Int,
    val sku: String,
)
