package inge2.com.alquileres.backend.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

//Manejador de excepicones general, captura todas las excepicones de la aplicacion
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<FieldError> errors = ex.getFieldErrors();
        ValidationException validationException = new ValidationException(errors, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(validationException.toErrorResponse(),headers,status);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<String> handleEntityExist(EntityExistsException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(MailException.class)
    public ResponseEntity<String> handlerMailException(MailException ex){
        return new ResponseEntity<>("La operación se completó correctamente, pero no se pudo enviar la notificación por email. Por favor, contacte al administrador del sistema.",HttpStatus.OK);
    }
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> handlerTokenException(JwtException ex){
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handlerExpiredTokenException(ExpiredJwtException ex){
        return new ResponseEntity<String>("La sesión del token caducó.", HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<String> handlerCredentialsNotFound(AuthenticationCredentialsNotFoundException ex){
        return new ResponseEntity<String>("El usuario no esta autenticado no se puede obtener el email del context", HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(UncheckedIOException.class)
    public ResponseEntity<String> ioExceptionHandler(IOException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> ilegalArgumentHandler(IllegalArgumentException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handlerDataIntegrityViolation(DataIntegrityViolationException ex){
        return new ResponseEntity<>("El dato que intentas ingresar ya existe",HttpStatus.CONFLICT);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleGenericException(RuntimeException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
