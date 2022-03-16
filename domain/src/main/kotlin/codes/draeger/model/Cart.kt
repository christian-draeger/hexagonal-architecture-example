package codes.draeger.model

import java.util.UUID

data class Cart(
    val id: UUID,
    val items: Set<CartItem> // look ma i'm immutable!
) {
    init {
        validateMaxPrice()
        validateMaxItemCount()
    }

    private val totalPriceInCents = items.sumOf { it.price.inCents }

    val totalPrice = totalPriceInCents.toPrice()
    val totalQuantity = items.sumOf { it.quantity }

    fun addItem(item: CartItem): Cart {
        val mergedItem = if (item in this) item.merge(getItemBySku(item.product.sku)) else item

        val updatedItems = items.toMutableSet().apply {
            remove(item)
            add(mergedItem)
        }

        return copy(items = updatedItems) // since items are immutable we make use of data classes build-in copy constructor
    }

    fun removeItem(item: CartItem): Cart = getItemBySku(item.product.sku)?.let {
        copy(items = items.toMutableSet().apply { remove(item) })
    } ?: this

    operator fun contains(item: CartItem): Boolean = getItemBySku(item.product.sku) != null

    private fun getItemBySku(sku: SKU) = items.find { it.product.sku == sku }

    private fun validateMaxPrice() {
        val maxTotalPriceInCents = 1000_00
        require(totalPriceInCents <= maxTotalPriceInCents) {
            "domain.Cart '$id' exceeded the maximum total price of $maxTotalPriceInCents with a value of $totalPrice"
        }
    }

    private fun validateMaxItemCount() {
        val maxItemsAmount = 100
        require(totalQuantity <= maxItemsAmount) {
            "domain.Cart '$id' exceeded the maximum amount of $maxItemsAmount items with a value of $totalQuantity"
        }
    }
}

data class CartItem(
    val product: Product,
    val quantity: Int // could alternatively use type UInt to enforce checking for none negative numbers
) {
    val price = Price(product.price.inCents * quantity)

    init {
        validateMaxAmount()
        validateNoneNegativeAmount()
    }

    fun increaseQuantity(increment: Int) = copy(quantity = quantity + increment)
    fun decreaseQuantity(decrement: Int) = copy(quantity = quantity - decrement)

    fun merge(item: CartItem?): CartItem {
        return item?.let {
            require(it.product.sku == product.sku) {
                "can only add items with the same product sku"
            }
            it.copy(quantity = it.quantity + quantity)
        } ?: this
    }

    operator fun plus(item: CartItem?): Set<CartItem> {
        return when {
            item == null -> setOf(this)
            product.sku == item.product.sku -> setOf(merge(item))
            else -> setOf(this, item)
        }
    }

    private fun validateMaxAmount() {
        val maxQuantity = 10
        require(quantity <= maxQuantity) {
            "cart item amount of '$quantity' exceeded the max amount of $maxQuantity"
        }
    }

    private fun validateNoneNegativeAmount() {
        require(quantity > 0) {
            "cart item amount needs to be at least 1 but was '$quantity'"
        }
    }
}
