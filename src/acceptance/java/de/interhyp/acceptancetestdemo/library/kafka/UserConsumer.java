package de.interhyp.acceptancetestdemo.library.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.interhyp.acceptancetestdemo.entity.User;
import de.interhyp.acceptancetestdemo.library.KafkaTestConsumerUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

public class UserConsumer {

    public static final String USER_TOPIC = "user-demo-topic";

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;
    @Autowired
    private ObjectMapper objectMapper;

    private Consumer<String, User> userConsumer;

    @Before(value = "@UserConsumer")
    public void createUserConsumer() {
        userConsumer = KafkaTestConsumerUtil.setUpConsumer(embeddedKafkaBroker,
            new StringDeserializer(), new JsonDeserializer<>(User.class, objectMapper));
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(userConsumer, USER_TOPIC);
    }

    public Consumer<String, User> getUserConsumer() {
        if (userConsumer == null) {
            throw new IllegalStateException("No consumer for User created. Use the value from @Before in the feature description for creation");
        }
        return userConsumer;
    }


    @After(value = "@UserConsumer")
    public void closeUserConsumer() {
        this.userConsumer.close();
        this.userConsumer = null;
    }

}
