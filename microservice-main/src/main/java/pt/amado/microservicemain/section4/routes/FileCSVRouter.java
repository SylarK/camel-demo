package pt.amado.microservicemain.section4.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(
        value = "section.enabled",
        havingValue = "4",
        matchIfMissing = true
)
public class FileCSVRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        // multicast
//        from("file:files/csv")
//                .multicast()
//                .to("activemq:multicast-route-1", "activemq:multicast-route-2", "activemq:multicast-route-3")
//                .log("Message sent to queues");

        //splitter
//        from("file:files/csv")
//                .unmarshal().csv()
//                .split(body())
//                .to("activemq:split-route");
//        from("file:files/csv")
//                .convertBodyTo(String.class)
//                .split(body(), ",")
//                //.split(method(splitComponent))
//                .log("Message sent")
//                .to("activemq:split-route");
    }

    @Component
    class SplitComponent{

        private List<String> splitAndReturnAList(String body){
            return List.of("Foo", "Bar", "Ha", "Uchi");
        }

    }
}
