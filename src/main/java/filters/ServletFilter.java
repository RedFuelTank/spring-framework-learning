package filters;

import filters.content_type_listener.WrapperListener;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;

@WebFilter(urlPatterns = {"/api/orders", "/orders/form"})
public class ServletFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Request: " + request.getContentType());
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        HttpServletResponseWrapper responseWrapper;
        HttpServletRequestWrapper requestWrapper;

//        System.out.println("Method: " + servletRequest.getMethod());

//        requestWrapper = WrapperListener
//                .getRequestWrapperFor(servletRequest);
//
//        servletResponse.setContentType(servletRequest.getHeader("Accept"));
//
//        responseWrapper = WrapperListener
//                .getResponseWrapperFor(servletResponse);
//
//        chain.doFilter(requestWrapper, responseWrapper);

        switch (servletRequest.getMethod()) {
            case "POST":
                requestWrapper = WrapperListener
                        .getRequestWrapperFor(servletRequest);

                servletResponse.setContentType(servletRequest.getHeader("Accept"));

                responseWrapper = WrapperListener
                        .getResponseWrapperFor(servletResponse);

                chain.doFilter(requestWrapper, responseWrapper);
                break;
            case "GET":
                servletResponse.setContentType(servletRequest.getHeader("Accept"));

                responseWrapper = WrapperListener
                        .getResponseWrapperFor(servletResponse);

                chain.doFilter(servletRequest, responseWrapper);

        }

//        switch (request.getContentType()) {
//            case "application/json":
//                requestWrapper = new JsonRequestWrapper((HttpServletRequest) request);
//                break;
//            case "application/x-www-form-urlencoded":
//                requestWrapper = new UrlEncodedRequestWrapper((HttpServletRequest) request);
//                break;
//            default:
//                throw new IllegalArgumentException();
//        }


//        switch (requestWrapper.getHeader("Accept")) {
//            case "application/json":
//                responseWrapper = new JsonResponseWrapper((HttpServletResponse) response);
//                responseWrapper.setHeader("Content-Type", "application/json");
//                break;
//            case "application/x-www-form-urlencoded":
//                responseWrapper = new UrlEncodedResponseWrapper((HttpServletResponse) response);
//                responseWrapper.setHeader("Content-Type", "application/x-www-form-urlencoded");
//                break;
//            default:
//                responseWrapper = new JsonResponseWrapper((HttpServletResponse) response);
//                responseWrapper.setHeader("Content-Type", "application/json");
//        }

        System.out.println("Response: " + response.getContentType());
    }


}
