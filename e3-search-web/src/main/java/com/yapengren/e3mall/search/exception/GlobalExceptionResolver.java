package com.yapengren.e3mall.search.exception;

import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

/**
 * 全局异常处理器
 *
 * @author renyapeng
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ex.printStackTrace();
        //把异常写入日志文件
        logger.debug("==========");
        logger.debug(handler.getClass().toString());
        logger.debug("==========");
        logger.info("系统发生异常，进入全局异常处理器");
        logger.error("", ex);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/exception");
        modelAndView.addObject("key", "value");
        return modelAndView;
    }
}
