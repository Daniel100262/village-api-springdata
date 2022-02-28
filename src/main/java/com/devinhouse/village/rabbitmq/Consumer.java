package com.devinhouse.village.rabbitmq;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.devinhouse.village.model.transport.VillageReportDTO;
import com.devinhouse.village.rabbitmq.service.RabbitmqService;


@Component
public class Consumer {
	
	@Autowired
	RabbitmqService rabbitmqService;

    @RabbitListener(queues = {"${queue.name}"})
    public void receive(@Payload VillageReportDTO report) throws Exception {
    	rabbitmqService.sendReportByEmail("daniel100262@hotmail.com", report);

    }

}
