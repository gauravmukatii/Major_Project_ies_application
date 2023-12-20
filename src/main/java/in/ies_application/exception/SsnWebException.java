package in.ies_application.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class SsnWebException extends RuntimeException{

    public SsnWebException() {
    }

    public SsnWebException(String message) {
        super(message);
    }
}
