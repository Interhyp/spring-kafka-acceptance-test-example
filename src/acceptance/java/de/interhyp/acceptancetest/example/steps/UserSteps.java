package de.interhyp.acceptancetest.example.steps;

import de.interhyp.acceptancetest.example.config.SpringAcceptanceTest;
import de.interhyp.acceptancetest.example.entity.User;
import de.interhyp.acceptancetest.example.library.AwaitilityHelper;
import de.interhyp.acceptancetest.example.library.KafkaTestConsumerUtil;
import de.interhyp.acceptancetest.example.library.kafka.UserConsumer;
import de.interhyp.acceptancetest.example.library.rest.TestRestClient;
import de.interhyp.acceptancetest.example.repository.UserRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class UserSteps extends SpringAcceptanceTest {

    @Autowired
    private TestRestClient testRestClient;
    @Autowired
    private UserConsumer userConsumer;
    @Autowired
    private UserRepository userRepository;


    @Given("A user with id={string} and name {string}")
    public void aUserWithIdAndName(String id, String name) {
        final User user = User.builder()
            .id(id)
            .name(name)
            .build();
        testRestClient.saveUser(user);
        AwaitilityHelper.wait(() -> userRepository.findById(id).isPresent());
    }

    @When("A user with id={string} and name {string} is created")
    public void aUserWithIdAndNameIsCreated(String id, String name) {
        final User user = User.builder()
            .id(id)
            .name(name)
            .build();

        testRestClient.saveUser(user);
    }

    @Then("The user with id={string} and name {string} should be written on the topic")
    public void theUserWithIdAndNameShouldBeWrittenOnTheTopic(String id, String name) {
        final Consumer<String, User> consumer = this.userConsumer.getUserConsumer();
        final ConsumerRecord<String, User> record = KafkaTestConsumerUtil.consume(id, consumer, UserConsumer.USER_TOPIC);

        assertThat(record).isNotNull();
        User user = record.value();
        assertThat(user.id()).isEqualTo(id);
        assertThat(user.name()).isEqualTo(name);
    }

    @When("The name of the user with id={string} changes to {string}")
    public void theNameOfTheUserWithIdChangesTo(String id, String name) {
        final User user = User.builder()
            .id(id)
            .name(name)
            .build();

        testRestClient.saveUser(user);
    }

    @Then("The user with id={string} and name {string} should be written to the database")
    public void theUserWithIdAndNameShouldBeWrittenToTheDatabase(String id, String name) {
        AwaitilityHelper.wait(() -> userRepository.findById(id).isPresent());

        final User user = userRepository.findById(id).get();
        assertThat(user.id()).isEqualTo(id);
        assertThat(user.name()).isEqualTo(name);
    }

    @Then("The name of user with id={string} should have changed to {string}")
    public void theNameOfUserWithIdShouldHaveChangedTo(String id, String name) {
        AwaitilityHelper.wait(() -> userRepository.findById(id).isPresent() && userRepository.findById(id).get().name().equals(name));

        final User user = userRepository.findById(id).get();
        assertThat(user.id()).isEqualTo(id);
        assertThat(user.name()).isEqualTo(name);
    }
}
