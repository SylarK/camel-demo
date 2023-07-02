package pt.amado.camelsamples._01.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import pt.amado.camelsamples._01.model.User;

@Component
public class ReceiverRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("file:{{camel.filepath}}")
                .routeId("Receive-Router-Main")
                .split().xpath("//record")
                .streaming()
                .convertBodyTo(String.class)  // Convert each record to String
                .unmarshal().jacksonXml(User.class)
                .to("log:logging-application");

    }

}
