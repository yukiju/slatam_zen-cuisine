package com.yukiju.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yukiju.repos.User;
import com.yukiju.services.RegisterService;


@RestController
@RequestMapping("/register")
@CrossOrigin(origins = "*",
methods = {RequestMethod.GET, RequestMethod.PUT,
		RequestMethod.PATCH, RequestMethod.POST},
allowedHeaders = {"*"})
public class RegisterController {
	
	static Logger logger = Logger.getRootLogger();
	
	@Autowired
	RegisterService regService;
	
	/*
	 * Posting a user through Postman is success
	 * tried with a few fields only { id username, email, password, firstName, lastName }
	 * NOTE registering a new user is using jpa persist instead of save id MUST be 0 or
	 * exception will be thrown
	 * 
	 * Posting a user through the form below on the Get mapping will throw a CORS error on:
	 * Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported
	 */
	@PostMapping @ResponseStatus(HttpStatus.CREATED)
	public void register(@RequestBody User user) {
		
		logger.info("REGISTERING");
		logger.info(user);
		regService.addNewUser(user);
	}
	/*
	 * 
	 * This is just for serverside testing this is not the actual client side
	 * more for admin stuff maybe but will be removed after client side is up
	 */
	@GetMapping(produces = MediaType.TEXT_HTML_VALUE)
	public String register() {
		String html = "<!DOCTYPE html>\n" + 
				"<html>\n" + 
				"<head>\n" + 
				"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n" + 
				"<title>RaceForCash</title>\n" + 
				"<link rel=\"shortcut icon\" href=\"resources/imgs/favicon.png\" type=\"image/x-icon\">\n" + 
				"</head>\n" + 
				"<body id=\"page-top\" class=\"index\">\n" + 
				"<nav class=\"navbar navbar-default navbar-fixed-top\">\n" + 
				"	<div class=\"container\">\n" + 
				"		<div class=\"navbar-header page-scroll\">\n" + 
				"		</div>\n" + 
				"	</div>\n" + 
				"</nav>\n" + 
				"<header>\n" + 
				"	<div class=\"container\">\n" + 
				"    <br>\n" + 
				"    <br>\n" + 
				"    <br>\n" + 
				"    <br>\n" + 
				"    <h3>Open a new account</h3><hr>\n" + 
				"    <form action=\"./register\" method=\"post\" class=\"form-horizontal\" id=\"register\">\n" + 
				"                <input type=\"hidden\" class=\"form-control\" id=\"id\" name=\"id\" value=\"0\" required=\"required\"/>\n" + 
				"        <div class=\"form-group\">\n" + 
				"            <label for=\"username\" class=\"col-sm-4 control-label\">Username</label>\n" + 
				"            <div class=\"col-sm-4\">\n" + 
				"                <input type=\"text\" class=\"form-control\" id=\"username\" name=\"username\" placeholder=\"username\" required=\"required\"/>\n" + 
				"            </div>\n" + 
				"        </div>\n" + 
				"        <div class=\"form-group\">\n" + 
				"            <label for=\"email\" class=\"col-sm-4 control-label\">Email</label>\n" + 
				"            <div class=\"col-sm-4\">\n" + 
				"                <input type=\"email\" class=\"form-control\" id=\"email\" name=\"email\" placeholder=\"email\" required=\"required\"/>\n" + 
				"            </div>\n" + 
				"        </div>\n" + 
				"        <div class=\"form-group\">\n" + 
				"            <label for=\"password\" class=\"col-sm-4 control-label\">Password</label>\n" + 
				"            <div class=\"col-sm-4\">\n" + 
				"                <input type=\"password\" class=\"form-control\" id=\"password\" name=\"password\" placeholder=\"password\" required=\"required\"/>\n" + 
				"            </div>\n" + 
				"        </div>\n" + 
				"        <div class=\"form-group\">\n" + 
				"            <label for=\"firstName\" class=\"col-sm-4 control-label\">First Name</label>\n" + 
				"            <div class=\"col-sm-4\">\n" + 
				"                <input type=\"text\" class=\"form-control\" id=\"firstName\" name=\"firstName\" placeholder=\"First Name\"/>\n" + 
				"            </div>\n" + 
				"        </div>\n" + 
				"        <div class=\"form-group\">\n" + 
				"            <label for=\"lastName\" class=\"col-sm-4 control-label\">Last Name</label>\n" + 
				"            <div class=\"col-sm-4\">\n" + 
				"                <input type=\"text\" class=\"form-control\" id=\"lastName\" name=\"lastName\" placeholder=\"Last Name\"/>\n" + 
				"            </div>\n" + 
				"        </div>\n" + 
				"        <div class=\"form-group\">\n" + 
				"            <div class=\"col-sm-4\">\n" + 
				"                <input type=\"submit\" />\n" + 
				"            </div>\n" + 
				"        </div>\n" + 
				"        <div class=\"form-group\">\n" + 
				"            <div class=\"col-sm-offset-4 col-sm-1\">\n" + 
				"              <button type=\"submit\" class=\"btn btn-primary\" onclick=\"document.register.submit()\">Add</button>\n" + 
				"            </div>\n" + 
				"          </div>\n" + 
				"    </form>\n" + 
				"\n" + 
				"	</div>\n" + 
				"</header>\n" + 
				"<footer class=\"text-center\">\n" + 
				"</footer>\n" + 
				"<script>\n" + 
				"    function submit() {\n" + 
				"        document.register.submit();\n" + 
				"    }\n" + 
				"</script>\n" + 
				"</body>\n" + 
				"</html>";
		return html;
	}

}
