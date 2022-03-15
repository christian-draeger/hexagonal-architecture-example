package codes.draeger.shoppingcart.ports.driven.productservice

import Name
import Price
import Product
import SKU
import org.springframework.stereotype.Service

@Service
class MockedProductRepository : ProductRepositoryPort {
    override fun findProductBySku(sku: SKU): Product? {
        return when (sku.value) {
            "12345678" -> Product(SKU("12345678"), Price(499_99), Name("PS5"))
            "1234567890" -> Product(SKU("1234567890"), Price(375_42), Name("Xbox One"))
            "AAABBBCCC" -> Product(SKU("AAABBBCCC"), Price(299_00), Name("Nintendo Switch"))
            else -> null
        }
    }
}
