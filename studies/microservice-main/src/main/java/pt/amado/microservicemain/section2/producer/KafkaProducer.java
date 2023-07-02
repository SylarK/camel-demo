package pt.amado.microservicemain.section2.producer;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        value = "section.enabled",
        havingValue = "2",
        matchIfMissing = true
)
public class KafkaProducer extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("file:files/json")
                .to("kafka:kafka-topic-sample");

    }
}
