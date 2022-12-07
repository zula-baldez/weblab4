package com.example.lab4.security;

import com.example.lab4.model.User;
import com.example.lab4.model.UsersDbController;
import com.example.lab4.util.HashCoder;
import com.example.lab4.util.JWTDTO;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChecker {
    @Autowired
    private HashCoder hashCoder;
    @Autowired
    private JwtTokenRepository jwtTokenRepository;
    @Autowired
    private UsersDbController usersDbController;
    private boolean checkLogin(String password, Optional<User> user) {
        return hashCoder.hash(password).equals(user.get().getPassword());
    }
    public ResponseEntity<String> generateJWTAccess(String login, String password) {
        Optional<User> user = usersDbController.findByLogin(login);
        if(user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        boolean isPassCorrect = checkLogin(password, user);
        if(isPassCorrect) {
            String token = jwtTokenRepository.generateAccessToken(user.get());
            JWTDTO jwtdto = new JWTDTO();
            jwtdto.setJWT(token);
            Gson gsoner = new Gson();
            return new ResponseEntity<>(gsoner.toJson(jwtdto), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        }

    }
}
