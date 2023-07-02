package pt.amado.microservicemain.section3.routes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Header;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConditionalOnProperty(
        value = "section.enabled",
        havingValue = "3",
        matchIfMissing = true
)
@RequiredArgsConstructor
public class FileRouter extends RouteBuilder {

    final DeciderBean deciderBean;

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
                    .when(method(deciderBean))
                        .log("Bean called")
                    .otherwise()
                        .log("NOT A XML FILE!")
                .end()
                .to("file:files/output");

        from("direct:log-files")
                .log("${messageHistory} ${file:absolute.path}")
                .log("${header:CamelMessageTimestamp}")
                .log("${file:name} ${file:name.ext} ${file:name.noext} ${file:onlyname}")
                .log("${routeId} ${camelId} ${body}");
    }
}

@Component
@ConditionalOnProperty(
        value = "section.enabled",
        havingValue = "3",
        matchIfMissing = true
)
@Slf4j
class DeciderBean {

    public boolean isValid(@Body String body,
                           @Headers Map<String, String> headers,
                           @Header("CamelFileAbsolutePath") String header,
                           @ExchangeProperties Map<String, String> properties) {

        log.info("{}\n{}\n{}\n{}", body, headers, header, properties);

        return true;
    }

}
