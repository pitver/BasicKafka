package ru.vershinin.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vershinin.service.KafkaProducer;


@RestController
@RequestMapping("/employee")
public class EmployeeController {


    @Autowired
    KafkaProducer kafkaProducer;

    @GetMapping
    public void publish(@RequestParam int index) {
        kafkaProducer.sendMessage("send " + index);

    }

}

