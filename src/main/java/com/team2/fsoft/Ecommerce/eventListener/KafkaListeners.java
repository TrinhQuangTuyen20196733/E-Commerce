package com.team2.fsoft.Ecommerce.eventListener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
   @KafkaListener(topics="payment",groupId = "groupId")
    void  listener(String data) {
        System.out.println("Receiving message: "+data+" done!");
    }

}
