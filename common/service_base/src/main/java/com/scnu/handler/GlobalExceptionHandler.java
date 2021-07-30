package com.scnu.handler;

import com.scnu.utils.ExceptionUtil;
import com.scnu.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     *  此处Result(service_utils)所在的模块与GlobalExceptionHandler(service_base)所在的模块不一样,需要在本模块中引入Result的模块解决问题
     *  即在service_base模块引入service_utils模块
     *
     *  然后根据依赖传递,在原来service模块中,删除service_utils
     *  因为service模块中引用了service_base模块,顺带就引入了service_utils
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result error(RuntimeException e){
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().message(e.getMessage());
    }
}
