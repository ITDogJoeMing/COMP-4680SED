package com.comp4680.webportal.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.comp4680.webportal.models.CustomException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    private final Logger logger = LoggerFactory.getLogger(CustomErrorController.class.getName());

    @GetMapping("/customError")
    @PostMapping("/customError")
    public ModelAndView handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        CustomException err = new CustomException();

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                err = new CustomException("GEN-404", String.format("Page not found! [%s]", request.getRequestURI()),
                        "/");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                err = new CustomException("GEN-500",
                        "Server error!" + request.getAttribute(RequestDispatcher.ERROR_MESSAGE).toString(), "/");
                logger.error(request.getAttribute(RequestDispatcher.ERROR_MESSAGE).toString());// log the error detail
            }
        }

        if (err.getErrMsg() == null || err.getErrMsg().isEmpty()) {
            err = new CustomException("GEN-ERR", "Unknown Error Occur!", "/");
        }
        logger.error(err.toString());
        return new ModelAndView("error", "ErrObject", err);
    }
}