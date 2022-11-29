package com.example.lab4.controller;

import com.example.lab4.model.Attempt;
import com.example.lab4.model.AttemptDbController;
import com.example.lab4.model.UsersDbController;
import com.example.lab4.util.HashCoder;
import com.example.lab4.util.LoginDTO;
import com.example.lab4.util.TableData;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class TableController {
    @Autowired
    private UsersDbController usersDbController;
    @Autowired
    private AttemptDbController attemptDbController;
    @Autowired
    private HashCoder hashCoder;
    @Autowired
    private RegistrationAndLoginController registrationAndLoginController;

    @PostMapping("/get_table_data")
    @CrossOrigin
    public ResponseEntity<String> getTableData(@RequestBody LoginDTO loginDTO) {
        ResponseEntity<String> response = registrationAndLoginController.checkLogin(loginDTO);
        if(response.getStatusCode() == HttpStatus.OK) {
            List<Attempt> attemptList = ( List<Attempt> ) attemptDbController.findAll();


            Gson gson = new Gson();
            return new ResponseEntity<>(gson.toJson(attemptList.toArray()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}