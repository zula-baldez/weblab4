package com.example.lab4.util;

import com.example.lab4.model.Attempt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetTableAnsDTO {
    private List<Attempt> attemptList;
    private long tableSize;
}
