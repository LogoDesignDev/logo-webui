package com.example.provider.service;

import org.springframework.stereotype.Service;

@Service
public class TickService {

    public String getTicket(){
        return "《西游记》";
    }
}
