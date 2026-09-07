package com.example.linkedInProject.postsService.advice;


import com.example.linkedInProject.postsService.exception.APIError;
import com.example.linkedInProject.postsService.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIError> resourceNotFound(ResourceNotFoundException rnfe) {

        APIError apiError = returnAPIError(rnfe,HttpStatus.NOT_FOUND);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(apiError);

    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<APIError> validationErrors(MethodArgumentNotValidException manve) {
//
//        //getting all the binding errors from the exception and converting it to List<String> using stream
//        List<String> errorList = manve.getBindingResult()
//                .getAllErrors()
//                .stream().map(error->error.getDefaultMessage())
//                .collect(Collectors.toList());
//
//        APIError apiError = returnAPIError(manve,HttpStatus.BAD_REQUEST);
//        apiError.setHttpStatus(HttpStatus.BAD_REQUEST);
//        apiError.setSubErrors(errorList);
//
//        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
//    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIError> internalServerError(Exception exception) {

        APIError apiError = returnAPIError(exception,HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //return APIError, with exception message, and current date,time
    APIError returnAPIError(Exception exception, HttpStatus statusCode){
        return new APIError(exception.getMessage(),statusCode);
    }
}