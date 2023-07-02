package pt.amado.microservicemain.section4.routes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Header;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import pt.amado.microservicemain.section2.producer.model.CurrentExchange;
import pt.amado.microservicemain.section4.util.ArrayListAggregationStrategy;

import java.util.List;
import java.util.Map;

@Component
@ConditionalOnProperty(
        value = "section.enabled",
        havingValue = "4",
        matchIfMissing = true
)
@RequiredArgsConstructor
public class FileCSVRouter extends RouteBuilder {

    @Autowired
    DynamicRouterBean dynamicRouterBean;

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

        //aggregate
//        from("file:files/aggregate-json")
//                .unmarshal().json(JsonLibrary.Jackson, CurrentExchange.class)
//                .aggregate(simple("${body.to}"), new ArrayListAggregationStrategy())
//                //.completionFromBatchConsumer()
//                .completionSize(3)
//                .convertBodyTo(String.class)
//                .log("Message sent")
//                .to("activemq:aggregate-json-route");

        //slip
//        String paths = "direct:slip-endpoint-1,direct:slip-endpoint-2";
//        from("timer:slip-pattern?period=8000")
//                .transform().constant("Foo Bar")
//                .routingSlip(simple(paths));
//
//        from("direct:slip-endpoint-1")
//                .to("log:slip-endpoint-1");
//
//        from("direct:slip-endpoint-2")
//                .to("log:slip-endpoint-2");

        //dynamic routing
        from("timer:dynamic-pattern?period=8000")
                .transform().constant("Default messsage to dynamic pattern")
                .dynamicRouter(method(dynamicRouterBean));

        from("direct:dynamic-endpoint-1")
                .to("log:dynamic-endpoint-1");

        from("direct:dynamic-endpoint-2")
                .to("log:dynamic-endpoint-2");
    }

    @Component
    class SplitComponent{

        private List<String> splitAndReturnAList(String body){
            return List.of("Foo", "Bar", "Ha", "Uchi");
        }

    }


}

@Component
class DynamicRouterBean {

    Logger log = LoggerFactory.getLogger(DynamicRouterBean.class);

    int invocations;

    public String decideTheNextEndpoint(@ExchangeProperties Map<String, String> properties,
                                        @Headers Map<String, String> headers,
                                        @Body String body) {

        log.info("{} {} {}", properties, headers, body);
        invocations++;

        if(invocations == 1)
            return "direct:dynamic-endpoint-1";
        if(invocations == 2)
            return "direct:dynamic-endpoint-2";

        invocations = 0;
        return null;
    }

}