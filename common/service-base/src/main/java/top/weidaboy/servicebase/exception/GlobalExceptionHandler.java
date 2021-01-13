package top.weidaboy.servicebase.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.weidaboy.commonutils.R;
import top.weidaboy.commonutils.util.ExceptionUtil;

/**
 * @ControllerAdvice 全局异常处理， 全局数据绑定 ， 全局数据预处理
 * 例如专门处理空指针的方法、专门处理数组越界的方法...，
 * 也可以直接向上面代码一样，在一个方法中处理所有的异常信息。
 * @ExceptionHandler 注解用来指明异常的处理类型，
 * 即如果这里指定为 NullpointerException，则数组越界异常就不会进到这个方法中来。
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return R.error();
    }
}
