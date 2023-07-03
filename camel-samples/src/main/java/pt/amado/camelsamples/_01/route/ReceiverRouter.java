package pt.amado.camelsamples._01.route;

import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import pt.amado.camelsamples._01.model.User;
import pt.amado.camelsamples._01.processor.UserTransformerCamel;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReceiverRouter extends RouteBuilder {

    private final UserTransformerCamel userTransformer;

    @Override
    public void configure() throws Exception {

        from("file:{{camel.filepath}}")
                .routeId("Receive-Router-Main")
                .split(xpath("//record"), new AggregationStrategyUsers())
                    .streaming()
                    .unmarshal().jacksonXml(User.class)
                    .process(userTransformer::doSomething)
                .end()
                .bean(userTransformer , "generateResponse")
                .to("log:logging-application")
                .to("activemq:route-request-cc");


    }

    private static class AggregationStrategyUsers implements org.apache.camel.AggregationStrategy {
        @Override
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

            @SuppressWarnings("unchecked")
            List<User> userList = oldExchange != null ? oldExchange.getIn().getBody(List.class) : new ArrayList<>();
            User newUser = newExchange.getIn().getBody(User.class);
            userList.add(newUser);

            newExchange.getIn().setBody(userList);
            return newExchange;
        }
    }
}
