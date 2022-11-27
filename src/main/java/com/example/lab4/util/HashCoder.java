package com.example.lab4.util;

import com.google.common.hash.Hashing;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@ComponentScan
public class HashCoder {
    public String hash(String text) {
        return Hashing.sha256()
                .hashString(text, StandardCharsets.UTF_8)
                .toString();
    }
}
