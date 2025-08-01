package it.softengunina.userservice.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestUtils {
    private static final Logger log = LoggerFactory.getLogger(RequestUtils.class);

    private RequestUtils() { }

    public static void printAuthorizationHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            log.info("Authorization = {}", authHeader);
        } else {
            log.warn("Authorization header is missing.");
        }
    }
}
