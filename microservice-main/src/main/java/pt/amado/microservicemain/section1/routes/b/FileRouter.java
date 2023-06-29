package pt.amado.microservicemain.section1.routes.b;

import org.apache.camel.builder.RouteBuilder;

//@Component
public class FileRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file:files/input")
                .log("${body}")
                .to("file:files/output");
    }
}
