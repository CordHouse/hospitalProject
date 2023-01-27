package com.example.hospitalproject.Advice;

import com.example.hospitalproject.Exception.Payment.*;
import com.example.hospitalproject.Exception.ChatBoard.NotFoundChatBoardException;
import com.example.hospitalproject.Exception.ChatBoard.NotFoundChattingException;
import com.example.hospitalproject.Exception.ChatBoard.NotMatchSenderDeleteException;
import com.example.hospitalproject.Exception.RefreshToken.NotFoundRefreshTokenException;
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

    @ExceptionHandler(NotFoundRefreshTokenException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response notFoundRefreshTokenException() {
        return Response.failure(404, "해당 사용자의 정보와 일치하는 토큰을 찾지 못하였습니다.");
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

    @ExceptionHandler(NotFoundChattingException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response notFoundChattingException(NotFoundChattingException e) {
        return Response.failure(404, e.getMessage());
    }

    @ExceptionHandler(NotMatchSenderDeleteException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response notMatchSenderDeleteException() {
        return Response.failure(404, "송신자만 삭제 할 수 있습니다.");
    }

    @ExceptionHandler(NotFoundBankException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response notFoundBankException() {
        return Response.failure(404, "존재하지 않는 은행입니다.");
    }

    @ExceptionHandler(DuplicateCardInfoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response duplicateCardInfoException() {
        return Response.failure(404, "이미 등록된 카드입니다.");
    }

    @ExceptionHandler(NotFoundPayTypeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response notFoundPayTypeException() {
        return Response.failure(404, "결제 타입이 존재하지 않습니다.");
    }

    @ExceptionHandler(PayCancelException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response payCancelException(PayCancelException e) {
        return Response.failure(404, e.getMessage());
    }

    @ExceptionHandler(NotFoundCardException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response notFoundCardException(NotFoundCardException e) {
        return Response.failure(404, e.getMessage());
    }

    @ExceptionHandler(NotFoundCardListException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response notFoundCardListException() {
        return Response.failure(404, "해당 계정은 등록된 카드가 없습니다.");
    }

    @ExceptionHandler(NotFoundCardChoiceException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response notFoundCardChoiceException() {
        return Response.failure(404, "선택된 카드가 없습니다.");
    }
}
