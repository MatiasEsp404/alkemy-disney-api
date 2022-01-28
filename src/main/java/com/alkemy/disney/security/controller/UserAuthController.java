package com.alkemy.disney.security.controller;

import com.alkemy.disney.security.dto.AuthenticationRequest;
import com.alkemy.disney.security.dto.AuthenticationResponse;
import com.alkemy.disney.security.dto.UserDto;
import com.alkemy.disney.security.service.JwtUtils;
import com.alkemy.disney.security.service.UserDetailsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/auth")
public class UserAuthController {

    @Autowired
    private UserDetailsCustomService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtTokenUtil;

    @PostMapping(path = "/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody UserDto user) throws Exception{
        userDetailsService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(path = "/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authRequest) throws Exception{

        UserDetails userDetails;
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()));
            userDetails = (UserDetails) auth.getPrincipal();

        } catch (BadCredentialsException err){
            throw new Exception("Incorrect username or password", err);
        }
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
