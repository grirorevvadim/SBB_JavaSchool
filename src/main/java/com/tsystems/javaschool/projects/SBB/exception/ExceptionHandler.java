package com.tsystems.javaschool.projects.SBB.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@ControllerAdvice
public class ExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.error("An error was caught in ExceptionHandler class. Error message is: " + ex.getMessage() + " Cause is: " + ex.getCause());
        String message = "There is a server error. Please try later";
        return new ModelAndView("error", "errormsg", message);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView resolveEntityNotFoundException(HttpServletRequest request, EntityNotFoundException exception) {
        log.warn(exception.getMessage());
        return new ModelAndView("error-404");
    }
}
