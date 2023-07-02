package pt.amado.microservicemain.section1.routes.b;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        value = "section.enabled",
        havingValue = "1",
        matchIfMissing = true
)
public class FileRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file:files/input")
                .log("${body}")
                .to("file:files/output");
    }
}
