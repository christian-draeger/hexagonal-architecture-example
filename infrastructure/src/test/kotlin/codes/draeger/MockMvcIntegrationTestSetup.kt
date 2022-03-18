package codes.draeger

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockHttpServletRequestDsl
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl
import org.springframework.test.web.servlet.ResultActionsDsl

@SpringBootTest
@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc
@ActiveProfiles("testdata")
abstract class MockMvcIntegrationTestSetup : TestContainer() {
    @Autowired
    protected lateinit var mockMvc: MockMvc

    protected fun <T> ResultActionsDsl.andExpectBody(type: Class<T>, init: T.() -> Unit) {
        with(andReturn().response) {
            jacksonObjectMapper().readValue(contentAsString, type).init()
        }
    }

    protected fun <T> MockHttpServletRequestDsl.withJsonBody(t: T) {
        contentType = MediaType.APPLICATION_JSON
        accept = MediaType.APPLICATION_JSON
        content = jacksonObjectMapper().writeValueAsString(t)

    }

    protected fun <T> MockMvcResultMatchersDsl.jsonBody(init: () -> T) {
        content { contentType(MediaType.APPLICATION_JSON) }
        content { json(jacksonObjectMapper().writeValueAsString(init())) }
    }
}
