package pt.amado.cameldemo.section2.producer;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQProducer extends RouteBuilder {

    static {
        System.out.println("LOADED!!!");
    }
    @Override
    public void configure() throws Exception {
        from("timer:active-mq-timer?period=5000")
                .transform().constant("Constant message to ActiveMQ")
                .log("${body}")
                .to("activemq:my-activemq-queue");
    }
}
