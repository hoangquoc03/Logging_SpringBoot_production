package com.example.session16;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestInterceptorTests {

    private final RequestInterceptor interceptor = new RequestInterceptor();

    @AfterEach
    void clearMdc() {
        MDC.clear();
    }

    @Test
    void assignsUuidTraceIdAndRemovesOnlyThatKeyAfterCompletion() {
        MDC.put("existingKey", "preserved");
        HttpServletRequest request = new MockHttpServletRequest("GET", "/test");
        HttpServletResponse response = new MockHttpServletResponse();

        assertTrue(interceptor.preHandle(request, response, new Object()));

        String traceId = MDC.get("traceId");
        assertNotNull(traceId);
        assertEquals(traceId, UUID.fromString(traceId).toString());

        interceptor.afterCompletion(request, response, new Object(), null);

        assertNull(MDC.get("traceId"));
        assertEquals("preserved", MDC.get("existingKey"));
    }
}
