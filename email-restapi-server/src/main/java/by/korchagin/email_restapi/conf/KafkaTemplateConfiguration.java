package by.korchagin.email_restapi.conf;



import by.korchagin.email_restapi.dto.Message;
import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaTemplateConfiguration {

    public static final String EMAIL_TOPIC = "email_topic";

    @Bean
    @SneakyThrows
    public ProducerFactory<String, Message> producerFactory() {
        Map<String, Object > props = new HashMap<>();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, InetAddress.getLocalHost().getHostName());
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.RECONNECT_BACKOFF_MS_CONFIG, 1000);
        props.put(ProducerConfig.ACKS_CONFIG, "all");

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, Message> kafkaTemplate(ProducerFactory<String,
            Message> producerFactory) {
        KafkaTemplate<String, Message> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setDefaultTopic(EMAIL_TOPIC);
        return kafkaTemplate;
    }

}