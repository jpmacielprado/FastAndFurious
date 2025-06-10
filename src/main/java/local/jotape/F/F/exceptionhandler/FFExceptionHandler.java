package local.jotape.F.F.exceptionhandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FFExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        ProblemaException problema = new ProblemaException();
        problema.setStatus(status.value());
        problema.setTitulo("Um ou mais campos inválidos, tente novamente!!");
        problema.setDatahora(LocalDateTime.now());

        List<ProblemaException.CampoProblema> camposComErro = new ArrayList<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String nomeCampo = ((FieldError) error).getField();
            String mensagemCampo = error.getDefaultMessage();

            camposComErro.add(new ProblemaException.CampoProblema(nomeCampo, mensagemCampo));
        }

        problema.setCampos(camposComErro);

        return new ResponseEntity<>(problema, headers, status);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleEnumInvalido(MethodArgumentTypeMismatchException ex, WebRequest request) {
        ProblemaException problema = new ProblemaException();
        problema.setStatus(400);
        problema.setTitulo("Valor inválido para um dos campos. Verifique os campos e tente novamente.");
        problema.setDatahora(LocalDateTime.now());

        return handleExceptionInternal(ex, problema, new HttpHeaders(), org.springframework.http.HttpStatus.BAD_REQUEST, request);
    }
}
