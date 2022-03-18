package codes.draeger.config

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerErrorHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException) =
        ResponseEntity(
            ErrorMessageDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.message
            ),
            HttpStatus.BAD_REQUEST
        )

    data class ErrorMessageDTO(
        val status: Int?,
        val message: String?
    )
}
