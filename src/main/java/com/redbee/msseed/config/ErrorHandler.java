package com.redbee.msseed.config;

import brave.Tracer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    private final HttpServletRequest httpServletRequest;
    private final Tracer tracer;

    public ErrorHandler(final Tracer tracer, final HttpServletRequest httpServletRequest) {
        this.tracer = tracer;
        this.httpServletRequest = httpServletRequest;
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handle(Throwable ex) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex);
        return buildResponseError(HttpStatus.INTERNAL_SERVER_ERROR,ex, ErrorCode.INTERNAL_ERROR);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handle(MissingServletRequestParameterException ex) {
        log.error(ErrorCode.INVALID_PARAMETERS_ERROR.getCode(), ex);
        return buildResponseError(HttpStatus.BAD_REQUEST,ex, ErrorCode.INVALID_PARAMETERS_ERROR);
    }

    private ResponseEntity<ErrorResponse> buildCustomResponseError(HttpStatus httpStatus, ErrorCode errorCode, String customDescription) {
        final var response = ErrorResponse.builder()
                .errorInternalCode(errorCode.value())
                .errorDescription(customDescription)
                .errorCode(errorCode.getCode())
                .build();

        return new ResponseEntity<>(response, httpStatus);
    }





    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ErrorResponse {

        private static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss[.SSSSSSSSS]['Z']";

        @JsonProperty
        private String name;
        @JsonProperty
        private Integer status;

        @JsonProperty
        private Integer code;

        @JsonProperty
        int errorInternalCode;
        @JsonProperty
        String errorDescription;
        @JsonProperty
        String errorCode;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_FORMAT)
        private LocalDateTime timestamp;
        @JsonProperty
        private String resource;
        @JsonProperty
        private String detail;
        @JsonProperty
        private Map<String, String> metadata;
    }


    private ResponseEntity<ErrorResponse> buildResponseError(HttpStatus httpStatus, Throwable ex, ErrorCode errorCode) {


        var traceId = Optional.ofNullable(this.tracer.currentSpan())
                .map(span -> span.context().traceIdString())
                .orElse(TraceSleuthInterceptor.TRACE_ID_NOT_EXISTS);

        var spandId = Optional.ofNullable(this.tracer.currentSpan())
                .map(span -> span.context().spanIdString())
                .orElse(TraceSleuthInterceptor.SPAND_ID_NOT_EXISTS);


        var queryString = Optional.ofNullable(httpServletRequest.getQueryString())
                .orElse("");

        var metaData = Map.of(
                TraceSleuthInterceptor.X_B3_TRACE_ID, traceId,
                TraceSleuthInterceptor.X_B3_SPAN_ID, spandId,
                "query_string", queryString);

        final ErrorResponse apiErrorResponse = ErrorResponse
                .builder()
                .timestamp(LocalDateTime.now())
                .name(httpStatus.getReasonPhrase())
                .detail(String.format("%s: %s", ex.getClass().getCanonicalName(), ex.getMessage()))
                .status(httpStatus.value())
                .code(errorCode.value())
                .errorInternalCode(errorCode.value())
                .resource(httpServletRequest.getRequestURI())
                .metadata(metaData)
                .errorInternalCode(errorCode.value())
                .errorDescription(errorCode.getDetail())
                .errorCode(errorCode.getCode())
                .build();

        return new ResponseEntity<>(apiErrorResponse, httpStatus);
    }

}

