package com.rodrigopisati.Final_backend.services.imp;


import com.rodrigopisati.Final_backend.entities.User;
import com.rodrigopisati.Final_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserDetailsService {


    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> searchUser = userService.findByEmail(username);
        if(searchUser.isPresent()) {
            return searchUser.get();
        }else{
            throw new UsernameNotFoundException("Debe ingresar email correcto");
        }
    }
}
