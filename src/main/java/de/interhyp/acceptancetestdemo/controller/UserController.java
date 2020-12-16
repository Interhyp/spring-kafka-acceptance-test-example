package de.interhyp.acceptancetestdemo.controller;

import de.interhyp.acceptancetestdemo.entity.User;
import de.interhyp.acceptancetestdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.UUID;

import static de.interhyp.acceptancetestdemo.listener.UserListener.USER_TOPIC;

@RestController
public class UserController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private UserRepository userRepository;

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
