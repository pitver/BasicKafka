package ru.vershinin.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Listener {

    private AtomicInteger messageCount = new AtomicInteger(0);
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private Random random = new Random();


    @KafkaListener(topics = "my_topic", groupId = "my-consumer-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(String message) {
        int currentMessageCount = messageCount.incrementAndGet();
        long threadId = Thread.currentThread().getId();
        System.out.println("Received message " + currentMessageCount + ": " + message + " on thread: " + threadId);

        // Retry up to 5 times to get true from getRandomBoolean()
        int retryAttempts = 5;
        boolean randomBoolean = getRandomBooleanWithRetry(retryAttempts);

        if (randomBoolean) {
            System.out.println("получили true" + currentMessageCount + ": " + message);

        } else {
            System.out.println("после " + retryAttempts + " попыток. Cannot proceed with message " + currentMessageCount + ": " + message);
        }

        System.out.println("Обработка завершена " + currentMessageCount + ": " + message + " on thread: " + threadId);
    }


    private boolean getRandomBooleanWithRetry(int retryAttempts) {
        for (int attempt = 1; attempt <= retryAttempts; attempt++) {

            try {
                Thread.sleep(10000); // Simulate the timeout here (10 seconds)
                System.out.println("attempt "+attempt);
            } catch (InterruptedException e) {
                // Handle the exception if needed
                e.printStackTrace();
            }

            boolean randomBoolean = getRandomBoolean();
            if (randomBoolean) {
                return true;
            }
        }
        return false;
    }







    private boolean getRandomBoolean() {
        return random.nextBoolean();
    }
}
