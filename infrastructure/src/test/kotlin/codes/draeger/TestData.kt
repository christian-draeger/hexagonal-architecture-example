package codes.draeger

import codes.draeger.model.Cart
import codes.draeger.model.CartItem
import codes.draeger.model.Name
import codes.draeger.model.Price
import codes.draeger.model.Product
import codes.draeger.model.SKU
import java.util.UUID

fun aValidProduct(
    sku: SKU = SKU.randomSKU(),
    price: Price = Price(4711),
    name: Name = Name("A test domain.Product"),
) = Product(
    sku = sku,
    price = price,
    name = name,
)

fun aValidCartItem(
    product: Product = aValidProduct(),
    quantity: Int = 5
) = CartItem(
    product = product,
    quantity = quantity,
)

fun anEmptyCart(
    id: UUID = UUID.randomUUID(),
    items: Set<CartItem> = emptySet()
) = Cart(
    id = id,
    items = items,
)

fun aValidCart(
    id: UUID = UUID.randomUUID(),
    items: Set<CartItem> = setOf(aValidCartItem())
) = anEmptyCart(
    id = id,
    items = items,
)
