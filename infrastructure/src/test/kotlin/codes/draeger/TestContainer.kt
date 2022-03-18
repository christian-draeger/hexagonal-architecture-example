package codes.draeger

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
@ContextConfiguration(initializers = [TestContainer.Initializer::class])
@Suppress("UtilityClassWithPublicConstructor")
abstract class TestContainer {

    companion object {
        @Container
        @JvmStatic
        internal val mongoDbContainer = CustomMongoDbContainer()
            .withExposedPorts(27017)
    }

    class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(context: ConfigurableApplicationContext) {
            TestPropertyValues.of(
                "spring.data.mongodb.host=${mongoDbContainer.containerIpAddress}",
                "spring.data.mongodb.port=${mongoDbContainer.getMappedPort(27017)}",
            ).applyTo(context.environment)
        }
    }
}

internal class CustomMongoDbContainer(
    version: String = "5.0.6",
) : MongoDBContainer(DockerImageName.parse("mongo:$version")) {
    init {
        logger.info("Starting MongoDBContainer")
    }
}
