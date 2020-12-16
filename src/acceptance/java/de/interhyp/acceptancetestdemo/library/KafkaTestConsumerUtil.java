package de.interhyp.acceptancetestdemo.library;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

public final class KafkaTestConsumerUtil {

    public static <K, V> Consumer<K, V> setUpConsumer(final EmbeddedKafkaBroker embeddedKafkaBroker,
                                                      final Deserializer<K> keySerde,
                                                      final Deserializer<V> valueSerde) {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(UUID.randomUUID().toString(), "true", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        DefaultKafkaConsumerFactory<K, V> cf = new DefaultKafkaConsumerFactory<>(consumerProps, keySerde, valueSerde);
        return cf.createConsumer();
    }

    public static <K, V> ConsumerRecord<K, V> consume(K id, Consumer<K, V> consumer, String topic) {
        return consume(consumer, topic, kvConsumerRecord -> kvConsumerRecord.key().equals(id));
    }

    public static <K, V> ConsumerRecord<K, V> consume(Consumer<K, V> consumer, String topic, final Predicate<? super ConsumerRecord<K, V>> condition) {
        final AtomicReference<ConsumerRecord<K, V>> poll = new AtomicReference<>();
        AwaitilityHelper.wait(() -> {
            Iterable<ConsumerRecord<K, V>> records = KafkaTestUtils.getRecords(consumer).records(topic);
            final ConsumerRecord<K, V> record = StreamSupport.stream(records.spliterator(), false)
                .filter(condition)
                .findFirst()
                .orElse(null);
            poll.set(record);
            return record != null;
        });
        return poll.get();
    }

}
