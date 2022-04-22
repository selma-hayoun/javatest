package com.atmira.javatest.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AsteroidsRequestInterceptor implements HandlerInterceptor {

    /**
     * This method is executed before the actual controller service method
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        return HandlerInterceptor.super.preHandle(request, response, handler);

        //The braces {} will accept any Object and uses its toString() method to build a message only after verifying that the log message is required.
        log.info("[PREHANDLE: Recepción de petición al controlador][{}]{}?{}", request.getMethod(), request.getRequestURI(), request.getQueryString());

        return true;

    }

    /**
     * This method is executed after the controller is ready to send the response
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);

        if (ex != null){
            ex.printStackTrace();
        }

        log.info("[AFTERCOMPLETION: Respuesta controlador]" + "[Https status: " + response.getStatus() + "]" + (ex==null?"":("][exception: " + ex + "]")));
    }
}
