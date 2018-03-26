package server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import server.Exceptions.APIException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles the exceptions of type Exceptions.
     * @param ex The exception caught.
     * @return The JSON representation of the exception. See @{@link APIException}
     */
    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleConflict(Exception ex) {
        String bodyOfResponse = "";
        HttpStatus status = null;

        // Handles custom error messages for different exceptions. Otherwise the default message in the error is used.
        if (ex instanceof NoSuchElementException) {
            bodyOfResponse = "The item you were searching for was not found.";
            status = HttpStatus.NOT_FOUND;
        }
        else {
            bodyOfResponse = ex.getLocalizedMessage();
            status = HttpStatus.BAD_REQUEST;
        }

        APIException apiException = new APIException(status, bodyOfResponse, Exception.class.getName());
        return new ResponseEntity<>(apiException, apiException.getStatus());
    }
}