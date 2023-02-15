package com.example.hospitalproject.Advice;

import com.example.hospitalproject.Exception.AuthorityAccessLimitException;
import com.example.hospitalproject.Exception.Board.*;
import com.example.hospitalproject.Exception.Comment.NotFoundCommentIdException;
import com.example.hospitalproject.Exception.Email.NotFoundEmailTokenInfoException;
import com.example.hospitalproject.Exception.Email.NotValidEmailException;
import com.example.hospitalproject.Exception.Payment.PayCancelException;
import com.example.hospitalproject.Exception.Payment.DuplicateCardInfoException;
import com.example.hospitalproject.Exception.Payment.NotFoundBankException;
import com.example.hospitalproject.Exception.Payment.*;
import com.example.hospitalproject.Exception.ChatBoard.NotFoundChatBoardException;
import com.example.hospitalproject.Exception.ChatBoard.NotFoundChattingException;
import com.example.hospitalproject.Exception.ChatBoard.NotMatchSenderDeleteException;
import com.example.hospitalproject.Exception.Payment.NotFoundPayTypeException;
import com.example.hospitalproject.Exception.RefreshToken.NotFoundRefreshTokenException;
import com.example.hospitalproject.Exception.UserException.LoginFailureException;
import com.example.hospitalproject.Exception.UserException.NotFoundUserException;
import com.example.hospitalproject.Exception.UserException.UserInfoDuplicationException;
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

    @ExceptionHandler(NotFoundUserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response userNameNotFoundException(NotFoundUserException e) {
        return Response.failure(404, e.getMessage());
    }

    @ExceptionHandler(UserInfoDuplicationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response userInfoDuplicationException(UserInfoDuplicationException e) {
        return Response.failure(409, e.getMessage());
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

    @ExceptionHandler(NotFoundBoardException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response notFoundBoardException(){
        return Response.failure(404, "존재하지 않는 게시글입니다.");
    }

    @ExceptionHandler(UserNameDifferentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response userNameDifferentException(){
        return Response.failure(400, "게시글의 작성자가 아닙니다.");
    }

    @ExceptionHandler(NotSupportedExtensionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response notSupportedExtensionException(NotSupportedExtensionException e) {
        return Response.failure(400, e + "는 지원하지 않는 확장자입니다. 파일을 다시 한 번 확인해주세요.");
    }

    @ExceptionHandler(SaveImageException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public Response saveImageException() {
        return Response.failure(406, "이미지 저장 과정 도중 문제가 발생하였습니다.");
    }

    @ExceptionHandler(NotInputStarPointException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response NotInputStarPointException(){
        return Response.failure(400, "해당하지 않는 별점입니다.");
    }

    @ExceptionHandler(NotFoundCommentIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response notFoundCommentIdException() {
        return Response.failure(404, "해당 댓글이 존재하지 않습니다.");
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

    @ExceptionHandler(CardSameStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response cardSameStatusException() {
        return Response.failure(404, "이미 선택된 카드입니다.");
    }

    @ExceptionHandler(AuthorityAccessLimitException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response authorityAccessLimitException() {
        return Response.failure(404, "접근권한이 없습니다.");
    }

    @ExceptionHandler(NotFoundEmailTokenInfoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response notFoundEmailTokenInfo() {
        return Response.failure(404, "토큰 정보가 유효하지 않습니다.");
    }

    @ExceptionHandler(NotValidEmailException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response notValidEmailException(NotValidEmailException e) {
        return Response.failure(404, e.getMessage());
    }
}
