package cn.idas_ouc.member.config;

import cn.idas_ouc.common.exception.GuiguException;
import cn.idas_ouc.common.utils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.AccessDeniedException;


@ControllerAdvice
public class GlobalExceptionHandler {

    //全局异常处理，执行的方法
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        e.printStackTrace();
        return R.error(40001,"执行全局异常处理...");
    }

    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error(40002,"执行特定异常处理...");
    }

    //自定义异常处理
    @ExceptionHandler(GuiguException.class)
    @ResponseBody
    public R error(GuiguException e) {
        e.printStackTrace();
        return R.error(40005, e.getMsg().toString());
    }

    /**
     * spring security异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public R error(AccessDeniedException e) throws AccessDeniedException {
        return R.error(40003,"没有操作权限");
    }
}
