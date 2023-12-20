package in.ies_application.exception;

import in.ies_application.binding.App;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(value = SsnWebException.class)
    public ResponseEntity<AppException> handleSsnWebEx(SsnWebException ex){
        AppException appEx = new AppException();
        appEx.setExCode("Ex001");
        appEx.setExDesc(ex.getMessage());

        return new ResponseEntity<AppException>(appEx, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
