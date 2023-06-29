package pt.amado.microservicemain.routes.b;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file:microservice-main/files/input")
                .log("${body}")
                .to("file:microservice-main/files/output");
    }
}
