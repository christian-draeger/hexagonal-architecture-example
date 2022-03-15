package codes.draeger.shoppingcart.ports.driven.productservice

import Product
import SKU
import org.springframework.stereotype.Service

@Service
interface ProductRepositoryPort {
    fun findProductBySku(sku: SKU): Product?
}
