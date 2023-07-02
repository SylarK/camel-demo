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
@RequiredArgsConstructor
public class ActiveMQReceiver extends RouteBuilder {

    private final CurrentExchangeProcessor currentExchangeProcessor;
    private final CurrentExchangeTransformer currentExchangeTransformer;

    @Override
    public void configure() throws Exception {
        from("activemq:my-activemq-queue")
                .unmarshal().json(JsonLibrary.Jackson, CurrentExchange.class)
                .bean(currentExchangeProcessor)
                .bean(currentExchangeTransformer)
                .to("log:received-message-from-my-activemq-queue");

        from("activemq:my-activemq-queue-file-xml")
                .unmarshal()
                .jacksonXml(CurrentExchange.class)
                .bean(currentExchangeProcessor)
                .to("log:received-message-from-my-activemq-queue-file-xml");
    }
}
