package com.alkemy.disney.security.service;

import com.alkemy.disney.exception.RegisterException;
import com.alkemy.disney.exception.msg.ExceptionMsg;
import com.alkemy.disney.security.dto.UserDto;
import com.alkemy.disney.security.model.UserModel;
import com.alkemy.disney.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;

@Service
public class UserDetailsCustomService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByUsername(username);
        if (userModel == null){
            throw new UsernameNotFoundException("Username or password not found");
        }
        return new User(userModel.getUsername(), userModel.getPassword(), Collections.emptyList());
    }

    public void register(UserDto userDto) throws IOException {
        UserModel user = new UserModel();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());

        if (userRepository.findByUsername(user.getUsername()) == null){
            user = userRepository.save(user);
            emailService.sendWelcomeEmailTo(user.getUsername());
        } else {
            throw new RegisterException(ExceptionMsg.USER_EXISTS);
        }
    }
}
