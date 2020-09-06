package com.springJwt.controller;


import com.springJwt.models.AuthentificationRequest;
import com.springJwt.models.AuthentificationResponse;
import com.springJwt.services.MyUserDetailsService;
import com.springJwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @RequestMapping({"/hello"})
    public String hello() {
        return "hello word";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthentificationToken(@RequestBody AuthentificationRequest authentificationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authentificationRequest.getUsername(), authentificationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("incorrect Username or password", e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authentificationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return  ResponseEntity.ok(new AuthentificationResponse(jwt));
    }
}

