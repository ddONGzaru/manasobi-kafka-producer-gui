package io.manasobi.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.connect.json.JsonSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created by twjang on 15. 10. 26.
 */
@Component
public class ProducerFactory {

    private static KafkaProducer<String, JsonNode> producer;

    public static KafkaProducer<String, JsonNode> getInstance() {

        if (producer == null) {
            producer = buildProducer();
        }
        return producer;
    }

    private static String metadataBrokerList;

    @Value("${kafka.metadata.broker.list}")
    public void setMetadataBrokerList(String metadataBrokerList) {
        ProducerFactory.metadataBrokerList = metadataBrokerList;
    }

    private static KafkaProducer<String, JsonNode> buildProducer() {

        Properties props = new Properties();
        props.put("bootstrap.servers", metadataBrokerList);
        //props.put("metadata.broker.list", metadataBrokerList);
        props.put("partitioner.class", RoundRobinPartitioner.class.getName());
        props.put("compression.codec", "2");
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", JsonSerializer.class.getName());

        //ProducerConfig producerConfig = new ProducerConfig(props);

        return new KafkaProducer<>(props);
    }
}