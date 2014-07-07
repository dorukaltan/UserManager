package com.infonal.userManager;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infonal.userManager.config.SpringMongoConfig;
import com.infonal.userManager.helper.UserHelper;
import com.infonal.userManager.model.User;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private String recaptchaPublicKey = "6LcY7_USAAAAALpehFFYUTBxYS8PqUze7BloifTE";
	private String recaptchaPrivateKey = "6LcY7_USAAAAAPTDSCjYMUyUU7SOlZxnFDJNMePJ";
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		ReCaptcha c = ReCaptchaFactory.newReCaptcha(recaptchaPublicKey, recaptchaPrivateKey, false);
	    String recaptchaHtml = c.createRecaptchaHtml(null, null);

	    List<User> userList = UserHelper.getAllUsers();

	    model.addAttribute("userList", userList );
	    model.addAttribute("recaptchaHtml", recaptchaHtml );
	    
		return "home";
	}
	
	@ResponseBody
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public String AddUser(Locale locale, Model model, HttpServletRequest request) {
		logger.info("Save user {}.", locale);
	    
	    User user = new User();
	    /*
	     * if id parameter is not null and not 'new', it means that an update operation will be made.
	     */
	    if(request.getParameter("id") != null && !request.getParameter("id").equals("new"))
	    	user.setId(request.getParameter("id"));
		else
		{
			/*
		     * if id parameter is null or 'new', reCaptcha validation should be performed.
		     */
			String recaptchaResponseField = request.getParameter("recaptchaResponseField");
			String recaptchaChallengeField = request.getParameter("recaptchaChallengeField");
			String remoteAddr = request.getRemoteAddr();

			ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
	        reCaptcha.setPrivateKey(recaptchaPrivateKey);

	        String challenge = recaptchaChallengeField;
	        String uresponse = recaptchaResponseField;
	        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);

	        if (!reCaptchaResponse.isValid())
	        {
	        	return "{'result': 'fail', 'errorReason', 'reCaptcha'}";
	        }
		}

	    user.setName(request.getParameter("name"));
	    user.setLastname(request.getParameter("lastname"));
	    user.setPhonenumber(request.getParameter("phonenumber"));


	    boolean saveUserResult = UserHelper.saveUser(user);

		return saveUserResult ? "success" : "fail";
	}
	
	@ResponseBody
	@RequestMapping(value = "/removeUser", method = RequestMethod.POST)
	public String RemoveUser(@RequestParam("id") String id) {


	    User user = new User();
	    user.setId(id);
	    boolean removeUserResult = UserHelper.removeUser(user);

	    return removeUserResult ? "success" : "fail";
	}
}
