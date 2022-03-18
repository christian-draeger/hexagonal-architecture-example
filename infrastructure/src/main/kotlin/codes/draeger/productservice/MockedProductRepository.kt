package codes.draeger.productservice

import codes.draeger.model.Name
import codes.draeger.model.Price
import codes.draeger.model.Product
import codes.draeger.model.SKU
import codes.draeger.service.ProductServicePort
import org.springframework.stereotype.Service

@Service
class MockedProductRepository : ProductServicePort {
    override fun findProductBySku(sku: SKU): Product? {
        return when (sku.value) {
            "12345678" -> Product(SKU("12345678"), Price(499_99), Name("PS5"))
            "1234567890" -> Product(SKU("1234567890"), Price(375_42), Name("Xbox One"))
            "AAABBBCCC" -> Product(SKU("AAABBBCCC"), Price(299_00), Name("Nintendo Switch"))
            else -> null
        }
    }
}
