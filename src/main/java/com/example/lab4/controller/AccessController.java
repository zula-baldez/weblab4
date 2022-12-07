package com.example.lab4.controller;

import com.example.lab4.model.UsersDbController;
import com.example.lab4.model.User;
import com.example.lab4.security.PasswordChecker;
import com.example.lab4.util.HashCoder;
import com.example.lab4.util.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Controller
@ComponentScan
@CrossOrigin
public class AccessController {
    @Autowired
    private UsersDbController usersDbController;
    @Autowired
    private HashCoder hashCoder;
    @Autowired
    private PasswordChecker passwordChecker;

    @PostMapping("/try_login")
    public ResponseEntity<String> tryLogin(@RequestBody LoginDTO loginDTO) {
        return passwordChecker.generateJWTAccess(loginDTO.getLogin(), loginDTO.getPassword());

    }
    @PostMapping("/try_register")
        public ResponseEntity<String> tryRegister(@RequestBody LoginDTO loginDTO) {

        Optional<User> userOpt = usersDbController.findByLogin(loginDTO.getLogin());
        if(userOpt.isPresent() || loginDTO.getLogin() == null || loginDTO.getPassword() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setLogin(loginDTO.getLogin());
        user.setPassword(hashCoder.hash(loginDTO.getPassword()));
        usersDbController.save(user);
        return passwordChecker.generateJWTAccess(loginDTO.getLogin(), loginDTO.getPassword());
    }
}
