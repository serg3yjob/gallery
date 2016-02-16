package ru.service.gallery.appsuport.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class FailureAuthenticationHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
 
        // Handle exception and set URL to redirect
    	if(exception instanceof LoginUserDisabledException){
    		String url = "loginDisactivatedUser";
	        this.setDefaultFailureUrl(url);
	        super.onAuthenticationFailure(request, response, exception);
    	}
    	if(exception instanceof UsernameNotFoundException){
    		String url = "/contacts/loginfailed";
	        this.setDefaultFailureUrl(url);
	        super.onAuthenticationFailure(request, response, exception);
    	}    	
    }
}
