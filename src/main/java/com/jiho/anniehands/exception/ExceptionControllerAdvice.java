package com.jiho.anniehands.exception;

import com.jiho.anniehands.user.MemberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    // MemberException 대한 핸들러
    // 이 메서드는 @Valid 어노테이션을 사용한 유효성 검사에서 예외가 발생했을 때 실행된다.
    // 하지만, 컨트롤러의 메서드 인자로 BindingResult 객체를 포함하고 있을 경우, 스프링은 MethodArgumentNotValidException 예외를 발생시키지 않고,
    // 대신 BindingResult 객체에 유효성 검사 실패 정보를 저장한다.
    // 따라서, 컨트롤러 메서드에서 BindingResult를 사용하여 유효성 검사 실패를 직접 처리할 수 있으므로 이 예외 처리 메서드는 사용하지 않는다.
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
//        log.error("handleMethodArgumentNotValidException", ex);
//        final ErrorResponse response = ErrorResponse.of(CustomErrorCode.MISMATCHED_PASSWORD, ex.getBindingResult());
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(MemberException.class)
    public String memberExceptionHandler(MemberException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/user/signup";
    }




}
