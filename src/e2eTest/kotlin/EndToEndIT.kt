import io.restassured.RestAssured
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class EndToEndIT {

    companion object {
        @Container
        private val app = DockerComposeTestContainer()
    }

    private val baseClient = RestAssured.given().baseUri(app.backendUrl)

    @Test
    fun `can take new shopping cart`() {
        with(baseClient) {
            When {
                get("/api/v1/new")
            } Then {
                statusCode(200)
            }
        }
    }

    @Test
    fun `can get shopping cart`() {
        with(baseClient) {
            val anExistingCartId = with(baseClient) {
                When {
                    get("/api/v1/new")
                } Then {
                    statusCode(200)
                } Extract {
                    path<String>("id")
                }
            }

            When {
                get("/api/v1/$anExistingCartId")
            } Then {
                statusCode(200)
                body("id", equalTo(anExistingCartId))
            }
        }
    }

    @Test
    fun `can call health check`() {
        with(baseClient) {
            When {
                get("/actuator/health")
            } Then {
                statusCode(200)
            }
        }
    }

    @Test
    fun `can call swagger ui`() {
        with(baseClient) {
            When {
                get("/swagger-ui/index.html")
            } Then {
                statusCode(200)
            }
        }
    }

    @Test
    fun `can call prometheus metrics`() {
        with(baseClient) {
            When {
                get("/actuator/prometheus")
            } Then {
                statusCode(200)
                body(containsString("system_cpu_count"))
            }
        }
    }
}
