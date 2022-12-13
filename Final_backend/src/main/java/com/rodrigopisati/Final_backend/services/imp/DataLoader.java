package com.rodrigopisati.Final_backend.services.imp;

import com.rodrigopisati.Final_backend.entities.User;
import com.rodrigopisati.Final_backend.entities.UserRol;
import com.rodrigopisati.Final_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader  implements ApplicationRunner {

    @Autowired
    UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pass = passwordEncoder.encode("digital");
        String passs = passwordEncoder.encode("digitall");
        userService.save(new User("Nombre", "Usuario", "email@mail.com", pass, UserRol.ROLE_USER ));
        userService.save(new User("Jacobo", "krakar" ,"krakar@gmail.com", passs, UserRol.ROLE_ADMIN));
    }
}

