package codes.draeger.metrics

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Component


@Component
class CustomMeters(
    private val registry: MeterRegistry,
) {
    fun incrementShoppingCartSaved(cartId: String) {
        shoppingCartSaved(cartId).apply { increment() }
    }

    private fun shoppingCartSaved(cartId: String) = Counter
        .builder("shopping_cart_persisted")
        .tag("cartId", cartId)
        .register(registry)

}
