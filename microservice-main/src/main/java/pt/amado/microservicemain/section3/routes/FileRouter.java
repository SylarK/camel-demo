package pt.amado.microservicemain.section3.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("file:files/input")
                .routeId("File-Choices-Route")
                .log("${body}")
                .transform().body(String.class)
                .choice()
                    .when(simple("${file:ext} ends with 'xml'"))
                        .log("XML FILE!")
                    .when(simple("${body} contains 'USD'"))
                        .log("NOT A XML FILE BUT CONTAINS USD VALUE")
                    .otherwise()
                        .log("NOT A XML FILE!")
                .end()
                .to("direct://log-files")
                .to("file:files/output");

        from("direct:log-files")
                .log("${messageHistory} ${file:absolute.path}")
                .log("${header:CamelMessageTimestamp}")
                .log("${file:name} ${file:name.ext} ${file:name.noext} ${file:onlyname}")
                .log("${routeId} ${camelId} ${body}");
    }
}
