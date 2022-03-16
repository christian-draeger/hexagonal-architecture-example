package codes.draeger.productservice

import codes.draeger.model.Product
import codes.draeger.model.SKU
import org.springframework.stereotype.Service

@Service
interface ProductRepositoryPort {
    fun findProductBySku(sku: SKU): Product?
}
