package codes.draeger.shoppingcart.ports.driven.productservice

import domain.Product
import domain.SKU
import org.springframework.stereotype.Service

@Service
interface ProductRepositoryPort {
    fun findProductBySku(sku: SKU): Product?
}
