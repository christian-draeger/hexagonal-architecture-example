import java.util.UUID

const val aValidSKU_10chars = "1234AB567X"
const val aValidSKU_8chars = "AB12EFG3"
const val aValidSKU_12chars = "12345ABC567X"

fun aValidProduct(
    sku: SKU = SKU.randomSKU(),
    price: Price = Price(4711),
    name: Name = Name("A test Product"),
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
