package com.longlu.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author yangcan
 * 自定义异常处理类
 */
@ControllerAdvice
public class CustomExceptionResolver{
	
	private static Logger logger = LoggerFactory.getLogger(CustomExceptionResolver.class);

	@ExceptionHandler(value=Exception.class)
	public ModelAndView resolveException(Exception e) {
		ModelAndView modelAndView = new ModelAndView("error");
		modelAndView.addObject("error",e.getMessage());
		logger.error(e.getMessage(),e);
		return modelAndView;
	}

}
