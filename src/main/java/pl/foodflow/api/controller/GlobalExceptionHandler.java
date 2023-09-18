package pl.foodflow.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder errorMessage = new StringBuilder("Validation errors:");

        for (FieldError error : bindingResult.getFieldErrors()) {
            String fieldName = error.getField();
            String defaultMessage = error.getDefaultMessage();
            errorMessage.append("\n").append(fieldName).append(": ").append(defaultMessage);
        }

        log.error(errorMessage.toString(), ex);

        ModelAndView modelView = new ModelAndView("error");
        modelView.addObject("errorMessage", errorMessage.toString());
        return modelView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        String message = String.format("Other exception occurred: [%s]", ex.getMessage());
        log.error(message, ex);
        ModelAndView modelView = new ModelAndView("error");
        modelView.addObject("errorMessage", message);
        return modelView;
    }

}
