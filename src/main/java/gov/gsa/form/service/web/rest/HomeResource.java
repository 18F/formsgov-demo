package gov.gsa.form.service.web.rest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import java.util.logging.Logger;

@RestController
public class HomeResource {
	private static final Logger logger = Logger.getLogger(HomeResource.class.getName());
	@GetMapping("/")
	public ModelAndView redirectHome() {
		return new ModelAndView("redirect:/ui");
	}
}
