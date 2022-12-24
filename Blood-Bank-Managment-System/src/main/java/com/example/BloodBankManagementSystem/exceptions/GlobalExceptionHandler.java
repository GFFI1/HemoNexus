package com.example.BloodBankManagementSystem.exceptions;

import com.example.BloodBankManagementSystem.pojo.Response;
import com.example.BloodBankManagementSystem.pojo.Status;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response> handleNotFoundException(NotFoundException ex){
        Response response=new Response();
        response.setBody(null);
        response.setStatus(new Status(HttpStatus.NOT_FOUND.value(),false,ex.getMessage()));
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        //here we will try to show the errors in the form of a map.
        //the key is the field where the exception is generated and the value is the message that we want to print
        Response response=new Response<>();
        Map<String,String> res=new HashMap<>();
        ex.getAllErrors().forEach((objectError -> {
            String fieldName=((FieldError)objectError).getField();
            String message=objectError.getDefaultMessage();
            res.put(fieldName,message);
        }));
        response.setBody(res);
        response.setStatus(new Status(HttpStatus.OK.value(), false,"Exception occured in DTO Validation"));
        //this map will have all the fields which have violated the validation as mentioned in the DTO.
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Response> handleJwtException(JwtException ex){
        Response response=new Response();
        response.setBody(null);
        response.setStatus(new Status(HttpStatus.FORBIDDEN.value(),false,ex.getMessage()));
        return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Response> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex){
        Response response=new Response<>();
        response.setBody(null);
        response.setStatus(new Status(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, ex.getMessage()));
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
