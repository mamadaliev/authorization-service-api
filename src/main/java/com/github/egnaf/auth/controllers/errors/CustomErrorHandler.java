package com.github.egnaf.auth.controllers.errors;

import com.github.egnaf.auth.transfers.errors.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CustomErrorHandler implements ErrorController {

    private final ErrorAttributes errorAttributes;

    @Autowired
    public CustomErrorHandler(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping("/error")
    @ResponseBody
    public ErrorResponse error(WebRequest request, HttpServletResponse response) {
        return new ErrorResponse(response.getStatus(), getErrorAttributes(request));
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    private Map<String, Object> getErrorAttributes(WebRequest request) {
        return new HashMap<>(this.errorAttributes.getErrorAttributes(request, false));
    }
}
