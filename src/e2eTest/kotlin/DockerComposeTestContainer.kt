import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import java.io.File
import java.time.Duration

class DockerComposeTestContainer(
    file: File = File("docker-compose.yml")
) : DockerComposeContainer<DockerComposeTestContainer>(file) {
    init {
        withExposedService(
            "database_1",
            27017,
            Wait.forListeningPort()
        )
        withExposedService(
            "backend_1",
            8080,
            Wait.forHttp("/actuator/health")
                .forStatusCode(200)
                .withStartupTimeout(Duration.ofMinutes(1))
        )
        withBuild(true)
    }

    val backendUrl: String by lazy {
        buildString {
            append("http://")
            append(getServiceHost("backend_1", 8080))
            append(":")
            append(getServicePort("backend_1", 8080))
        }
    }
}

