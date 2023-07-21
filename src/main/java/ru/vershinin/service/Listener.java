package ru.vershinin.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Listener {

    private AtomicInteger messageCount = new AtomicInteger(0);

    @KafkaListener(topics = "my_topic", groupId = "my-consumer-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(String message) {
        int currentMessageCount = messageCount.incrementAndGet();
        long threadId = Thread.currentThread().getId();
        System.out.println("Получено сообщение " + currentMessageCount + ": " + message + " on thread: " + threadId);

        if(message.equals("send 1")){
            pause();
        }

        System.out.println("Обработано сообщение " + currentMessageCount + ": " + message + " on thread: " + threadId);
    }

    private void pause(){
        // Добавим задержку в 1 секунду
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}


