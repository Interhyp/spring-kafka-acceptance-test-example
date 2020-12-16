package de.interhyp.acceptancetest.example.controller;

import de.interhyp.acceptancetest.example.entity.User;
import de.interhyp.acceptancetest.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static de.interhyp.acceptancetest.example.listener.UserListener.USER_TOPIC;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UserRepository userRepository;

    @RequestMapping(
        value = {"/api/user"},
        produces = {"application/json"},
        consumes = {"application/json"},
        method = {RequestMethod.POST}
    )
    public ResponseEntity<Void> saveUser(@RequestBody User user) {
        final Message<User> message = MessageBuilder
            .withPayload(user)
            .setHeader(KafkaHeaders.MESSAGE_KEY, user.id())
            .setHeader(KafkaHeaders.TOPIC, USER_TOPIC)
            .build();
        kafkaTemplate.send(message);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(
        value = {"/api/user/{id}"},
        produces = {"application/json"},
        consumes = {"application/json"},
        method = {RequestMethod.GET}
    )
    public ResponseEntity<User> getUser(@PathVariable("id") String id) {
        final Optional<User> user = userRepository.findById(id);
        return ResponseEntity.of(user);
    }
}
