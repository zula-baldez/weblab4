package com.example.lab4.security;

import com.example.lab4.model.User;
import com.example.lab4.model.UsersRepository;
import com.example.lab4.util.HashCoder;
import com.example.lab4.util.JWTDTO;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class PasswordChecker {
    @Autowired
    private HashCoder hashCoder;
    @Autowired
    private JwtTokenRepository jwtTokenRepository;
    @Autowired
    private UsersRepository usersRepository;

    private final Map<String, String> refreshStorage = new HashMap<>();


    private boolean checkLogin(String password, Optional<User> user) {
        return hashCoder.hash(password).equals(user.get().getPassword());
    }

    public ResponseEntity<JWTDTO> generateTokens(String login, String password) {
        Optional<User> user = usersRepository.findByLogin(login);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        boolean isPassCorrect = checkLogin(password, user);
        if (isPassCorrect) {
            String tokenAccess = jwtTokenRepository.generateAccessToken(user.get());
            String tokenRefresh = jwtTokenRepository.generateRefreshToken(user.get());
            JWTDTO jwtdto = new JWTDTO();
            jwtdto.setAccess(tokenAccess);
            jwtdto.setRefresh(tokenRefresh);
            refreshStorage.put(user.get().getLogin(), tokenRefresh);

            return new ResponseEntity<>(jwtdto, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        }

    }


    public ResponseEntity<JWTDTO> getAccessToken(String refreshToken) {
        final Claims claims = jwtTokenRepository.getRefreshClaims(refreshToken);
        final String login = claims.getSubject();
        final String saveRefreshToken = refreshStorage.get(login);
        Optional<User> user = usersRepository.findByLogin(login);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (jwtTokenRepository.validateRefreshToken(refreshToken)) {
            String token = jwtTokenRepository.generateAccessToken(user.get());
            if (token != null && saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final String accessToken = jwtTokenRepository.generateAccessToken(user.get());
                JWTDTO jwtdto = new JWTDTO();
                jwtdto.setAccess(accessToken);
                return new ResponseEntity<>(jwtdto, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<JWTDTO> refresh(String refreshToken) {
        if (jwtTokenRepository.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtTokenRepository.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                Optional<User> user = usersRepository.findByLogin(login);
                if (!user.isPresent()) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                final String accessToken = jwtTokenRepository.generateAccessToken(user.get());
                final String newRefreshToken = jwtTokenRepository.generateRefreshToken(user.get());
                refreshStorage.put(user.get().getLogin(), newRefreshToken);
                JWTDTO jwtdto = new JWTDTO();
                jwtdto.setAccess(accessToken);

                jwtdto.setRefresh(newRefreshToken);
                return new ResponseEntity<>(jwtdto, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


}
