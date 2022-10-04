package filters.content_type_listener;

import filters.JsonRequestWrapper;
import filters.JsonResponseWrapper;
import filters.UrlEncodedRequestWrapper;
import filters.UrlEncodedResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;

public class HttpContentTypeListener {
    @RequestContentType(type = "application/json")
    public HttpServletRequestWrapper getJsonRequestWrapper(HttpServletRequest request) {
        return new JsonRequestWrapper(request);
    }

    @ResponseContentType(type = "application/json")
    public HttpServletResponseWrapper getJsonResponseWrapper(HttpServletResponse response) {
        try {
            return new JsonResponseWrapper(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestContentType(type = "application/x-www-form-urlencoded")
    public HttpServletRequestWrapper getUrlEncodedRequestWrapper(HttpServletRequest request) {
        return new UrlEncodedRequestWrapper(request);
    }

    @ResponseContentType(type = "application/x-www-form-urlencoded")
    public HttpServletResponseWrapper getUrlEncodedResponseWrapper(HttpServletResponse response) {
        try {
            return new UrlEncodedResponseWrapper(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
