package com.example.lab4.controller;

import com.example.lab4.model.UsersRepository;
import com.example.lab4.model.User;
import com.example.lab4.security.PasswordChecker;
import com.example.lab4.util.HashCoder;
import com.example.lab4.util.JWTDTO;
import com.example.lab4.util.LoginDTO;
import com.example.lab4.util.RefreshDTO;
import lombok.RequiredArgsConstructor;
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
public class AccessController {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private HashCoder hashCoder;
    @Autowired
    private PasswordChecker passwordChecker;
    @CrossOrigin
    @PostMapping("/try-login")
    public ResponseEntity<JWTDTO> tryLogin(@RequestBody LoginDTO loginDTO) {

        return passwordChecker.generateTokens(loginDTO.getLogin(), loginDTO.getPassword());

    }

    @CrossOrigin
    @PostMapping("/try-register")
        public ResponseEntity<JWTDTO> tryRegister(@RequestBody LoginDTO loginDTO) {

        Optional<User> userOpt = usersRepository.findByLogin(loginDTO.getLogin());
        if(userOpt.isPresent() || loginDTO.getLogin() == null || loginDTO.getPassword() == null || loginDTO.getPassword().length() < 2 || loginDTO.getLogin().length() < 2) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setLogin(loginDTO.getLogin());
        user.setPassword(hashCoder.hash(loginDTO.getPassword()));
        usersRepository.save(user);
        return passwordChecker.generateTokens(loginDTO.getLogin(), loginDTO.getPassword());
    }
    @CrossOrigin
    @PostMapping("/access")
    public ResponseEntity<JWTDTO> getNewAccessToken(@RequestBody RefreshDTO refreshDTO) {
        return passwordChecker.getAccessToken(refreshDTO.getRefresh());
    }
    @CrossOrigin
    @PostMapping("/refresh")
    public ResponseEntity<JWTDTO> getNewRefreshToken(@RequestBody RefreshDTO refreshDTO) {
        return passwordChecker.refresh(refreshDTO.getRefresh());

    }
}
