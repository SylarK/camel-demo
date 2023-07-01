package pt.amado.microservicesecondary.section4.receiver;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        value = "section.enabled",
        havingValue = "4",
        matchIfMissing = true
)
public class FileCSVReceiverRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("activemq:multicast-route-1")
                .log("Message received from multicast-route-1");

        from("activemq:multicast-route-2")
                .log("Message received from multicast-route-2");

        from("activemq:multicast-route-3")
                .log("Message received from multicast-route-3");

        from("activemq:split-route")
                .to("log:split-receiver-route-log");
    }

}
