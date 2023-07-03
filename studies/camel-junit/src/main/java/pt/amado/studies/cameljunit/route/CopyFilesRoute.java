package pt.amado.studies.cameljunit.route;

import org.apache.camel.builder.RouteBuilder;

public class CopyFilesRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file:files/input")
                .to("file:files/output");
    }

}
