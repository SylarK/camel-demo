package pt.amado.studies.cameljunit.route;

import lombok.SneakyThrows;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SampleMockRouteTest extends CamelTestSupport {

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new SampleMockRoute();
    }

    @Test
    @SneakyThrows
    public void sampleMockTest() {
        String expected = "Hello, World";

        MockEndpoint mock = getMockEndpoint("mock:output");
        mock.expectedBodiesReceived(expected);

        String input = "Hello, World";
        template.sendBody("direct:sampleInput", input);
        assertMockEndpointsSatisfied();
    }
}