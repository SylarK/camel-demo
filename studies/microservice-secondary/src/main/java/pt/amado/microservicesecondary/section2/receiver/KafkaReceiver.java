package pt.amado.microservicesecondary.section2.receiver;

import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import pt.amado.microservicesecondary.section2.model.CurrentExchange;
import pt.amado.microservicesecondary.section2.processor.CurrentExchangeProcessor;
import pt.amado.microservicesecondary.section2.transformer.CurrentExchangeTransformer;

@Component
@ConditionalOnProperty(
        value = "section.enabled",
        havingValue = "2",
        matchIfMissing = true
)
public class KafkaReceiver extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("kafka:kafka-topic-sample")
                .unmarshal()
                .json(JsonLibrary.Jackson, CurrentExchange.class)
                .to("log:received-message-from-kafka-topic-sample");
    }
}
