package com.placepass.search.infrastructure;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

/**
 * This filter class validates the request header containing a valid partner id
 * 
 * @author chanaka.k
 *
 */

public class ProductApiFilter extends GenericFilterBean {

    private List<String> partnerIds;

    public ProductApiFilter(List<String> partnerIds) {
        this.partnerIds = partnerIds;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (req.getHeader("partner-id") == null || !StringUtils.hasText(req.getHeader("partner-id"))) {
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpResponse.setHeader("pp-code", "PARTNER_ID_NOT_FOUND");
            httpResponse.setHeader("pp-message", "Required partner-id not specified in the request");

        } else if (!partnerIds.contains(req.getHeader("partner-id"))) {
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpResponse.setHeader("pp-code", "INVALID_PARTNER_ID");
            httpResponse.setHeader("pp-message", "provided partner-id is invalid");

        } else {
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
