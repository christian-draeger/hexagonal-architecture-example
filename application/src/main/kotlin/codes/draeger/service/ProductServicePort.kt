package codes.draeger.service

import codes.draeger.model.Product
import codes.draeger.model.SKU
import org.springframework.stereotype.Service

@Service
interface ProductServicePort {
    fun findProductBySku(sku: SKU): Product?
}
