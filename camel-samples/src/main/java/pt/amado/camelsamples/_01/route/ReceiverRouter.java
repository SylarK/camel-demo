package pt.amado.camelsamples._01.route;

import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import pt.amado.camelsamples._01.model.User;
import pt.amado.camelsamples._01.processor.UserTransformerCamel;

import java.util.ArrayList;
import java.util.List;

/**
 * The ReceiverRouter class is a Camel route builder that receive a xml file from a specified directory,
 * processes the data in the files, and sends the transformed data to an ActiveMQ destination.
 */
@Component
@RequiredArgsConstructor
public class ReceiverRouter extends RouteBuilder {

    private final UserTransformerCamel userTransformer;

    /**
     * Configures the Camel routes for receiving files, processing data, and sending transformed data to ActiveMQ.
     * Receiving a xml file, split it into a list of classes and using this class to create a response and send to target
     * endpoint
     * @throws Exception if an error occurs during configuration
     */
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


    /**
    * An implementation of the AggregationStrategy interface for aggregating user data.
    */
    private static class AggregationStrategyUsers implements org.apache.camel.AggregationStrategy {

        /**
         * Aggregates user data from the old and new exchanges.
         * @param oldExchange the previous exchange
         * @param newExchange the current exchange
         * @return the updated exchange with aggregated user data
         */
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
