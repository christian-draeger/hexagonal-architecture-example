package codes.draeger.config

import codes.draeger.service.ShoppingCartServicePort
import codes.draeger.persistence.ShoppingCartRepositoryAdapter
import codes.draeger.port.ShoppingCartService
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.Properties


@Configuration
class AppConfig(
    private val shoppingCartRepositoryAdapter: ShoppingCartRepositoryAdapter
) {

    @Bean
    @ConditionalOnMissingBean(BuildProperties::class)
    fun buildProperties(): BuildProperties {
        return BuildProperties(Properties())
    }

    @Bean
    fun shoppingCartService(): ShoppingCartService {
        return ShoppingCartServicePort(shoppingCartRepositoryAdapter)
    }

}
