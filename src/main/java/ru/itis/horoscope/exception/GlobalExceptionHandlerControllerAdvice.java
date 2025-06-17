package ru.itis.horoscope.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

    private static final String PROBLEM_DETAIL_TYPE = "/swagger-ui/index.html";

    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handleBadRequestException(HttpServletRequest request, BadRequestException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        problemDetail.setTitle("Некорректный запрос");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setType(URI.create(PROBLEM_DETAIL_TYPE));
        return problemDetail;
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ProblemDetail handlePasswordMismatchException(HttpServletRequest request, PasswordMismatchException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        problemDetail.setTitle("Ошибка валидации пароля");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setType(URI.create(PROBLEM_DETAIL_TYPE));
        return problemDetail;
    }

    @ExceptionHandler(ClientAlreadyExistsException.class)
    public ProblemDetail handleClientAlreadyExistsException(HttpServletRequest request, ClientAlreadyExistsException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
        problemDetail.setTitle("Конфликт данных");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setType(URI.create(PROBLEM_DETAIL_TYPE));
        return problemDetail;
    }

//    @ExceptionHandler(AppointmentsNotCancelledException.class)
//    public ProblemDetail handleAppointmentsNotCancelledException(HttpServletRequest request, AppointmentsNotCancelledException exception) {
//        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
//        problemDetail.setTitle("Конфликт данных");
//        problemDetail.setInstance(URI.create(request.getRequestURI()));
//        problemDetail.setType(URI.create(PROBLEM_DETAIL_TYPE));
//        return problemDetail;
//    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        StringBuilder message = new StringBuilder("Ошибки валидации полей: ");

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            message.append(String.format("[%s: %s] ", error.getField(), error.getDefaultMessage()));
        });

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message.toString().trim());
        problemDetail.setTitle("Ошибка валидации DTO");
        problemDetail.setInstance(URI.create(request.getDescription(false).replace("uri=", "")));
        problemDetail.setType(URI.create(PROBLEM_DETAIL_TYPE));

        return new ResponseEntity<>(problemDetail, headers, status);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ProblemDetail handleInvalidCredentialsException(HttpServletRequest request, InvalidCredentialsException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
        problemDetail.setTitle("Ошибка аутентификации");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setType(URI.create(PROBLEM_DETAIL_TYPE));
        return problemDetail;
    }

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(HttpServletRequest request, NotFoundException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problemDetail.setTitle("Ресурс не найден");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setType(URI.create(PROBLEM_DETAIL_TYPE));
        return problemDetail;
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ProblemDetail handleIllegalsException(HttpServletRequest request, Exception exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        problemDetail.setTitle("Ошибка сервера");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setType(URI.create(PROBLEM_DETAIL_TYPE));
        return problemDetail;
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ProblemDetail handleUnauthorizedAccessException(HttpServletRequest request, UnauthorizedException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
        problemDetail.setTitle("Ошибка аутентификации");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setType(URI.create(PROBLEM_DETAIL_TYPE));
        return problemDetail;
    }
}
