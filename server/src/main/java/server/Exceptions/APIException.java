package server.Exceptions;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

public class APIException {
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public APIException(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public APIException(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Collections.singletonList(error);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}