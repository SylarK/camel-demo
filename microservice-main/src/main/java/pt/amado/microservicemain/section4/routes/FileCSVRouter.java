package pt.amado.microservicemain.section4.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

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
        from("file:files/csv")
                .multicast()
                .routeId("File-CSV-Route")
                .log("${body}");
    }
}
