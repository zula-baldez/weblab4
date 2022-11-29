package com.example.lab4.controller;

import com.example.lab4.model.UsersDbController;
import com.example.lab4.model.User;
import com.example.lab4.util.HashCoder;
import com.example.lab4.util.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Controller
@ComponentScan
public class RegistrationAndLoginController {
    @Autowired
    private UsersDbController usersDbController;
    @Autowired
    private HashCoder hashCoder;

    public ResponseEntity<String> checkLogin(String login, String password) {
        Optional<User> userOpt = usersDbController.findByLogin(login);
        if(userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if((hashCoder.hash(password).equals(userOpt.get().getPassword()))) {
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


    @PostMapping("/try_login")
    @CrossOrigin
    public ResponseEntity<String> tryLogin(@RequestBody LoginDTO loginDTO) {
        return checkLogin(loginDTO.getLogin(), loginDTO.getPassword());
    }
    @PostMapping("/try_register")
    @CrossOrigin
    public ResponseEntity<String> tryRegister(@RequestBody LoginDTO loginDTO) {

        Optional<User> userOpt = usersDbController.findByLogin(loginDTO.getLogin());
        if(userOpt.isPresent() || loginDTO.getLogin() == null || loginDTO.getPassword() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setLogin(loginDTO.getLogin());
        user.setPassword(hashCoder.hash(loginDTO.getPassword()));
        usersDbController.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
