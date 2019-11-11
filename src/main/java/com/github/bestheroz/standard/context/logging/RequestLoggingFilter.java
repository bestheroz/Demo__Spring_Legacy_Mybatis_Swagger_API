package com.github.bestheroz.standard.context.logging;

import com.github.bestheroz.standard.context.filter.ReadableRequestWrapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class RequestLoggingFilter extends CommonsRequestLoggingFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLoggingFilter.class);
    private static final String REQUEST_PARAMETERS = "<{}>{}, parameters={}";

    @Override
    protected void beforeRequest(final HttpServletRequest request, final String message) {
        if (StringUtils.equals(request.getMethod(), "GET")) {
            LOGGER.info(REQUEST_PARAMETERS, request.getMethod(), new UrlPathHelper().getPathWithinApplication(request), "");
        } else {
            try {
                LOGGER.info(REQUEST_PARAMETERS, request.getMethod(), new UrlPathHelper().getPathWithinApplication(request), IOUtils.toString(((ReadableRequestWrapper) request).getInputStream(),
                        StandardCharsets.UTF_8));
            } catch (final IOException e) {
                LOGGER.warn(ExceptionUtils.getStackTrace(e));
            }
        }
    }

    @Override
    protected void afterRequest(final HttpServletRequest request, final String message) {
        // super.afterRequest(request, message);
    }
}
