package facebook.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

public class TransferException extends RuntimeException {

    public final HttpStatus httpStatus;

    public TransferException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }
}
