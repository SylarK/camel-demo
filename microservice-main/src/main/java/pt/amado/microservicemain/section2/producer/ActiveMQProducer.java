package pt.amado.microservicemain.section2.producer;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class ActiveMQProducer extends RouteBuilder {

    @Override
    public void configure() throws Exception {

//        from("timer:active-mq-timer?period=5000")
//                .transform().constant("Constant message sent to queue")
//                .log("${body}")
//                .to("activemq:my-activemq-queue");

        from("file:files/json")
                .to("activemq:my-activemq-queue");

        from("file:files/xml")
                .to("activemq:my-activemq-queue-file-xml");

    }
}
