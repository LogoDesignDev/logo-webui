package com.example.consumer.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.ticket.service.TicketProviderServiceImpl;

public class TickerConsumer {
    @Reference
    TicketProviderServiceImpl ticketProviderService;

    public void start()

}
