package com.example.lab4.controller;

import com.example.lab4.model.Attempt;
import com.example.lab4.model.AttemptsRepository;
import com.example.lab4.model.UsersRepository;
import com.example.lab4.security.UserDetails;
import com.example.lab4.util.AttemptDTO;
import com.example.lab4.util.GetTableAnsDTO;
import com.example.lab4.util.GetTableDTO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class TableController {
    @Autowired
    private AttemptsRepository attemptsRepository;

    @PostMapping("/get-table-data")
    @CrossOrigin
    public ResponseEntity<GetTableAnsDTO> getTableData(@RequestBody GetTableDTO getTableDTO, @AuthenticationPrincipal UserDetails user) {

        long id = user.getUser().getUserId();
        long size = attemptsRepository.countByAuthorIdEquals(id);

        if (getTableDTO.getPage() < 0) {
            getTableDTO.setPage((int) ((size - 1) / getTableDTO.getCount()));
        }
        List<Attempt> attemptList = (List<Attempt>) attemptsRepository.findAllByAuthorIdEquals(id, PageRequest.of(getTableDTO.getPage(), getTableDTO.getCount(), Sort.by("attempt").ascending()));

        GetTableAnsDTO getTableAnsDTO = new GetTableAnsDTO(attemptList, size);

        return new ResponseEntity<>(getTableAnsDTO, HttpStatus.OK);

    }

    @PostMapping("/attempt")
    @CrossOrigin
    public ResponseEntity<String> attempt(@RequestBody AttemptDTO attemptDTO, @AuthenticationPrincipal UserDetails user) {
        if(attemptDTO.getR() <= 0 || attemptDTO.getR() > 3) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        long startTime = System.nanoTime();
        Long startTimeMillis = System.currentTimeMillis();
        long id = user.getUser().getUserId();
        Attempt last = attemptsRepository.findTopByAuthorIdEqualsOrderByAttemptDesc(id);

        int attemptLast;
        if (last == null) {
            attemptLast = 1;
        } else {
            attemptLast = last.getAttempt() + 1;
        }

        Attempt attempt = new Attempt();
        attempt.configAttempt(attemptLast, attemptDTO.getX(), attemptDTO.getY(), attemptDTO.getR(), validateHit(attemptDTO.getX(), attemptDTO.getY(), attemptDTO.getR()),
                (-startTime + System.nanoTime()) / 1000, startTimeMillis, id);
        attemptsRepository.save(attempt);

        return new ResponseEntity<>(HttpStatus.OK);


    }


    private boolean validateHit(double x, double y, double r) {
        if (x >= 0 && (x * x + y * y < r * r / 4) && y <= 0) {
            return true;
        }

        if ((x <= 0) && (y >= 0) && (y <= r + 2 * x)) {
            return true;
        }

        if (x <= 0 && y <= 0 && x >= -r / 2 && y >= -r) {
            return true;
        }
        return false;

    }
}
