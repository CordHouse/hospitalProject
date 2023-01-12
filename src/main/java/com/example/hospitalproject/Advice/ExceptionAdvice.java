package com.example.hospitalproject.Advice;

import com.example.hospitalproject.Exception.ChatBoard.NotFoundChatBoardException;
import com.example.hospitalproject.Exception.UserException.LoginFailureException;
import com.example.hospitalproject.Exception.UserException.NotFoundUsernameException;
import com.example.hospitalproject.Response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Response.failure(400, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(LoginFailureException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response loginFailureException() {
        return Response.failure(404, "로그인에 실패하였습니다. 아이디 혹은 비밀번호를 다시 한 번 확인해주세요");
    }

    @ExceptionHandler(NotFoundUsernameException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response userNameNotFoundException(NotFoundUsernameException e) {
        return Response.failure(404, e.getMessage());
    }

    @ExceptionHandler(NotFoundChatBoardException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response notFoundChatBoardException(){
        return Response.failure(400, "채팅방이 존재하지 않습니다.");
    }
}
