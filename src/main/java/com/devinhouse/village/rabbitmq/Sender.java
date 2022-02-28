package com.devinhouse.village.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devinhouse.village.model.transport.VillageReportDTO;

@Component
public class Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    public void send(VillageReportDTO report) {
        rabbitTemplate.convertAndSend(this.queue.getName(), report);
    }
}
