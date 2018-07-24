package com.sample.service;

import com.sample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LuckyNumberService {

    @Autowired
    private Random random;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserRepository userRepository;

    @Scheduled(fixedRate = 10000L)
    void draw() {
        final int number = random.nextInt(10);
        messagingTemplate.convertAndSend("/topic/lucky-number", number);
        userRepository.findAllByLuckyNumbersNumber(number).forEach(user -> {
            messagingTemplate.convertAndSendToUser(user.getUsername(), "/queue/lucky-number", number);
        });
    }
}
