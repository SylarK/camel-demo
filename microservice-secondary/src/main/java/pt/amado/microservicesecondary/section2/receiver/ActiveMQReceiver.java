package pt.amado.microservicesecondary.section2.receiver;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;
import pt.amado.microservicesecondary.section2.receiver.model.CurrentExchange;

@Component
public class ActiveMQReceiver extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("activemq:my-activemq-queue")
                .unmarshal().json(JsonLibrary.Jackson, CurrentExchange.class)
                .to("log:received-message-from-my-activemq-queue");
    }
}
