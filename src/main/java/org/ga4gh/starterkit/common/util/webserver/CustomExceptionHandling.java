package org.ga4gh.starterkit.common.util.webserver;

import java.time.LocalDateTime;
import org.ga4gh.starterkit.common.constant.DateTimeConstants;
import org.ga4gh.starterkit.common.exception.CustomException;
import org.ga4gh.starterkit.common.exception.CustomExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomExceptions(CustomException err) {
        HttpStatus httpStatus = getCustomHttpStatus(err);
        return yieldResponseEntity(httpStatus, err);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
        MissingServletRequestParameterException ex, HttpHeaders headers,
        HttpStatusCode status, WebRequest request
    ) {
        return yieldResponseEntity(HttpStatus.BAD_REQUEST, ex);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
        HttpRequestMethodNotSupportedException ex, HttpHeaders headers,
        HttpStatusCode status, WebRequest request
    ) {
        return yieldResponseEntity(HttpStatus.METHOD_NOT_ALLOWED, ex);
    }

    private HttpStatus getCustomHttpStatus(CustomException err) {
        return err.getClass().getAnnotation(ResponseStatus.class).value();
    }

    private ResponseEntity<Object> yieldResponseEntity(HttpStatus httpStatus, Exception ex) {
        CustomExceptionResponse response = new CustomExceptionResponse();
        response.setTimestamp(LocalDateTime.now().format(DateTimeConstants.DATE_FORMATTER));
        response.setStatusCode(httpStatus.value());
        response.setError(httpStatus.getReasonPhrase());
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, httpStatus);
    }
}
