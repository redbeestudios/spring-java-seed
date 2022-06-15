package com.redbee.msseed.config;

import brave.Tracer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class TraceSleuthInterceptor implements HandlerInterceptor {

    public static final String TRACE_ID_NOT_EXISTS = "Trace id not exists";
    public static final String SPAND_ID_NOT_EXISTS = "Spand id not exists";
    public static final String X_B3_TRACE_ID = "X-B3-TraceId";
    public static final String X_B3_SPAN_ID = "X-B3-SpanId";
    private Tracer tracer;

    public TraceSleuthInterceptor(final Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) {

        final var traceId = Optional.ofNullable(this.tracer.currentSpan())
                .map(span -> span.context().traceIdString())
                .orElse(TRACE_ID_NOT_EXISTS);

        if (!response.getHeaderNames().contains(X_B3_TRACE_ID)) {
            response.addHeader(X_B3_TRACE_ID, traceId);
        }

        final var spandId = Optional.ofNullable(this.tracer.currentSpan())
                .map(span -> span.context().spanIdString())
                .orElse(SPAND_ID_NOT_EXISTS);


        if (!response.getHeaderNames().contains(X_B3_SPAN_ID)) {
            response.addHeader(X_B3_SPAN_ID, spandId);
        }

        return Boolean.TRUE;
    }
}

