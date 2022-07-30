package facebook.exeptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionHandlers {
    @ExceptionHandler(TransferException.class)
    public ResponseEntity<String> handleException(TransferException exception){
        return ResponseEntity.status(exception.httpStatus).body(exception.getMessage());
    }
}
