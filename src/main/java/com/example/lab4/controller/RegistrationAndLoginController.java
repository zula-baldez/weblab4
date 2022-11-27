package com.example.lab4.controller;

import com.example.lab4.model.DbController;
import com.example.lab4.model.User;
import com.example.lab4.util.HashCoder;
import com.example.lab4.util.LoginDataRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;
import java.util.Optional;

@Controller
@ComponentScan
public class RegistrationAndLoginController {
    @Autowired
    private DbController dbController;
    @Autowired
    private HashCoder hashCoder;
    @PostMapping("/try_login")
    @CrossOrigin
    public ResponseEntity<String> tryLogin(@RequestBody LoginDataRequest loginDataRequest) {
        Optional<User> userOpt = dbController.findByLogin(loginDataRequest.login());
        if(userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if((hashCoder.hash(loginDataRequest.password()).equals(userOpt.get().getPassword()))) {
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    @PostMapping("/try_register")
    @CrossOrigin
    public ResponseEntity<String> tryRegister(@RequestBody LoginDataRequest loginDataRequest) {

        Optional<User> userOpt = dbController.findByLogin(loginDataRequest.login());
        if(userOpt.isPresent() || loginDataRequest.login() == null || loginDataRequest.password() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
        User user = new User();
        user.setLogin(loginDataRequest.login());
        user.setPassword(hashCoder.hash(loginDataRequest.password()));
        dbController.save(user);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
