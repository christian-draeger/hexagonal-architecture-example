package codes.draeger.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.GroupedOpenApi
import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig(private val buildProperties: BuildProperties) {

    @Bean
    fun userApis(): GroupedOpenApi {
        return GroupedOpenApi.builder().group("web")
            .pathsToMatch("/api/info/v1/**")
            .build()
    }

    @Bean
    fun adminApis(): GroupedOpenApi {
        return GroupedOpenApi.builder().group("android app")
            .pathsToMatch("/android/v1/**")
            .build()
    }

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .components(Components())
            .info(
                Info()
                    .title("Shopping Cart API")
                    .version(buildProperties.version)
                    .description("Documentation for shopping cart API")
                    .contact(
                        Contact()
                            .email("info@draeger.codes")
                            .name("Christian Dräger")
                            .url("https://github.com/christian-draeger")
                    )
            )
    }
}
