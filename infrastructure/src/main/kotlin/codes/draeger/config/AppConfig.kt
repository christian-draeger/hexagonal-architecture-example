package codes.draeger.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.Properties


@Configuration
class AppConfig {

    @Bean
    @ConditionalOnMissingBean(BuildProperties::class)
    fun buildProperties(): BuildProperties {
        return BuildProperties(Properties())
    }

    /*@Bean
    fun shoppingCartService(shoppingCartRepository: ShoppingCartRepository): ShoppingCartService {
        return DomainShoppingCartService(shoppingCartRepository)
    }*/

}
