package Kafka.Producer.Exception;

import Kafka.Producer.Model.ErrorResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponseModel> handleException(Exception ex){
        ErrorResponseModel err = new ErrorResponseModel();
        err.setMessage(ex.getMessage());
        err.setStatusCode(HttpStatus.BAD_REQUEST.value());
        err.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }
}
