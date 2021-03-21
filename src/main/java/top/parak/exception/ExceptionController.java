package top.parak.exception;

//import com.sun.javaws.exceptions.InvalidArgumentException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * @author KHighness
 * @since 2021-03-20
 */

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(IOException.class)
    public String handleIOException(IOException e) {
        return "IOException => [" +  e.getMessage() + "]";
    }

//    @ExceptionHandler(InvalidArgumentException.class)
//    public String handleInvalidArgumentException(InvalidArgumentException e) {
//        return "InvalidArgumentException => [" +  e.getMessage() + "]";
//    }

}
