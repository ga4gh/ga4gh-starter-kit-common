package org.ga4gh.starterkit.common.util.webserver;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.apache.catalina.connector.RequestFacade;
import org.apache.catalina.connector.ResponseFacade;
import static org.ga4gh.starterkit.common.constant.StarterKitConstants.ADMIN;

public class AdminEndpointsFilter implements Filter {

    private int adminPort;

    public AdminEndpointsFilter(int adminPort) {
        this.adminPort = adminPort;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if (adminPort != 0) {

            // if a request is made to an admin endpoint via a non-admin port, disallow the request
            if (isRequestForAdminEndpoint(servletRequest) && servletRequest.getLocalPort() != adminPort) {
                ((ResponseFacade) servletResponse).setStatus(404);
                servletResponse.getOutputStream().close();
                return;
            }

            // if a request is made to a non-admin endpoint via the admin port, disallow the request
            if (!isRequestForAdminEndpoint(servletRequest) && servletRequest.getLocalPort() == adminPort) {
                ((ResponseFacade) servletResponse).setStatus(404);
                servletResponse.getOutputStream().close();
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void init(FilterConfig filterConfig) {

    }

    public void destroy() {

    }

    private boolean isRequestForAdminEndpoint(ServletRequest servletRequest) {
        return ((RequestFacade) servletRequest).getRequestURI().contains(ADMIN);
    }
}
