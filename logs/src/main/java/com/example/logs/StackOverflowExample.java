package com.example.logs;

import org.springframework.stereotype.Service;

@Service
public class StackOverflowExample {

    void execute() {
        execute();
    }
}
