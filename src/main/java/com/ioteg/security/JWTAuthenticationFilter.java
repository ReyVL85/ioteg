package com.ioteg.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ioteg.users.User;
import com.ioteg.users.UserDTO;

import static com.ioteg.security.SecurityConstants.*;

public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    

	private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    
    protected JWTAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
		super(defaultFilterProcessesUrl);
		 this.authenticationManager = authenticationManager;
	     this.userDetailsService = userDetailsService;	
	}
   
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) {
        try {
            User creds = new ObjectMapper()
                    .readValue(req.getInputStream(), User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
    	String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        
        new ObjectMapper().writeValue(res.getOutputStream(), new UserDTO((User) userDetailsService.loadUserByUsername(((User)auth.getPrincipal()).getUsername())));
    }
}