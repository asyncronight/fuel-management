package me.kadarh.mecaworks.gazoil.controller;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.gazoil.service.exceptions.ApplicationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

	@ExceptionHandler(value = {ApplicationException.class, MultipartException.class})
	public ModelAndView handle(HttpServletRequest request, Exception e) {
		/*if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
			throw e;*/
		ModelAndView mav = new ModelAndView();
		if (e instanceof MultipartException) {
			mav.addObject("exception", new Exception("Image upload échouée, vérifier la taille du fichier (max = 5MB)", e));
		} else {
			mav.addObject("exception", e);
		}
		mav.setViewName("error/exception");
		log.warn("Application exception thrown - url : [" + request.getMethod() + "]" + request.getRequestURI(), e);
		return mav;
	}

	@ExceptionHandler(value = Exception.class)
	public void handleGlobal(HttpServletRequest request, Exception e) throws Exception {
		log.error("Non application exception thrown - url :[" + request.getMethod() + "]" + request.getRequestURL(), e);
		throw e;
	}
}
