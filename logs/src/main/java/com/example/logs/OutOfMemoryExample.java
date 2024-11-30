package com.example.logs;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OutOfMemoryExample {

    void execute() {
        List<Long> idList = new ArrayList<>();

        for (long i = 0; true; i++) {
            idList.add(i);
        }
    }
}
