package me.splleat.relay.app.common

import io.github.oshai.kotlinlogging.KotlinLogging
import me.splleat.relay.core.common.BusinessException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    private val log = KotlinLogging.logger {}

    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        statusCode: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val status = HttpStatus.resolve(statusCode.value()) ?: HttpStatus.INTERNAL_SERVER_ERROR
        val body = ApiErrorResponse(
            code = status.name,
            message = status.reasonPhrase,
        )

        return super.handleExceptionInternal(ex, body, headers, statusCode, request)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errors = ex.bindingResult.fieldErrors.map {
            FieldErrorDetail(it.field, it.defaultMessage ?: "올바르지 않은 값입니다.")
        }
        val body = ApiErrorResponse("INVALID_INPUT", "입력값이 올바르지 않습니다.", errors)

        return ResponseEntity.badRequest().body(body)
    }

    @ExceptionHandler(BusinessException::class)
    fun handleBusiness(e: BusinessException): ResponseEntity<ApiErrorResponse> =
            ResponseEntity.status(e.errorCode.type.status).body(ApiErrorResponse.from(e.errorCode))

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleConflict(e: DataIntegrityViolationException): ResponseEntity<ApiErrorResponse> {
        log.warn(e) { "제약 조건 위반" }
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(ApiErrorResponse("CONFLICT", "요청이 기존 데이터와 충돌합니다."))
    }

    @ExceptionHandler(Exception::class)
    fun handleUnexpected(e: Exception): ResponseEntity<ApiErrorResponse> {
        log.error(e) { "처리되지 않은 예외" }

        return ResponseEntity.internalServerError()
            .body(ApiErrorResponse("INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다."))
    }
}
