package com.mgmt.user.filter;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.util.StringUtils.isEmpty;

@Component
public class RequestIdFilter extends OncePerRequestFilter {

	private static final String REQUEST_ID_HEADER = "requestId";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String requestId = request.getHeader(REQUEST_ID_HEADER);
			if (!isEmpty(requestId))
				MDC.put(REQUEST_ID_HEADER, requestId);
			filterChain.doFilter(request, response);
		} finally {
			MDC.remove(REQUEST_ID_HEADER);
		}
	}

}
