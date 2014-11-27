package com.shopstuffs.config;

import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * User: Jama
 * Date: 11/26/2014
 */
public class MethodRequestMatcher implements RequestMatcher {
    private final RequestMethod method;

    public MethodRequestMatcher(RequestMethod method) {
        this.method = method;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        if (method == null) {
            return false;
        }
        return request.getMethod().equals(method.name());

    }
}
