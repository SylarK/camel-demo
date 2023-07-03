package pt.amado.studies.cameljunit.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SampleDirectRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:sampleInput")
                .log("Received message is ${body} and Headers are ${headers}")
                .to("file:files/sampleOutput?fileName=output.txt");
    }

}
