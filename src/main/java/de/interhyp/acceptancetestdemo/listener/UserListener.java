package de.interhyp.acceptancetestdemo.listener;

import de.interhyp.acceptancetestdemo.entity.User;
import de.interhyp.acceptancetestdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserListener {

    public static final String USER_TOPIC = "user-demo-topic";
    private final UserRepository userRepository;

    @KafkaListener(topics = USER_TOPIC, containerFactory = "userKafkaListenerContainerFactory")
    public void listen(@Payload User user) {
        log.info("Received {}", user);
        userRepository.save(user);
    }

}
