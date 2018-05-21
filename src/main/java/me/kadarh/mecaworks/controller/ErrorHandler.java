package me.kadarh.mecaworks.controller;

import lombok.extern.slf4j.Slf4j;
import me.kadarh.mecaworks.service.exceptions.ApplicationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Global exception handling
 *
 * @author salah3x
 */
@ControllerAdvice
@Slf4j
public class ErrorHandler {

	@ExceptionHandler(value = {ApplicationException.class, MultipartException.class})
	public ModelAndView handle(HttpServletRequest request, Exception e) {
		/*if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
			throw e;*/
		ModelAndView mav = new ModelAndView();
		if (e instanceof MultipartException)
			e = new Exception("Image upload échouée, vérifier la taille du fichier (max = 5MB)", e);
		mav.addObject("exception", e);
		List<String> causes = new ArrayList<>();
		while (e.getCause() instanceof ApplicationException) {
			e = (Exception) e.getCause();
			causes.add(e.getMessage());
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
