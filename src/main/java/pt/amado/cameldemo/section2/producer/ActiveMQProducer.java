package pt.amado.cameldemo.section2.producer;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQProducer extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:active-mq-timer?period=5000")
                .transform().constant("Constant message sent to queue")
                .log("${body}")
                .to("activemq:my-activemq-queue");
    }
}
