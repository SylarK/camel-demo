package pt.amado.microservicemain.section3.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class RestApiConsumerRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        restConfiguration().host("localhost:8000");

        from("timer:rest-api-consumer?period=5000")
                .log("${body}")
                .setHeader("from", () -> "EUR")
                .setHeader("to", () -> "BRL")
                .to("rest:get:/api/currency-exchange/from/{from}/to/{to}")
                .log("${body}");

    }

}
