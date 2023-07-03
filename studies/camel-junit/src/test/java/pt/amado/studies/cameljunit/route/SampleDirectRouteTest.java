package pt.amado.studies.cameljunit.route;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class SampleDirectRouteTest extends CamelTestSupport {

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new SampleDirectRoute();
    }

    @Test
    @SneakyThrows
    public void shouldReadTheInputAndSaveInATxtFile() {

        template.sendBody("direct:sampleInput", "Hello, World! Can you hear me?");
        Thread.sleep(5000);

        File file = new File("files/sampleOutput");
        assertTrue(file.isDirectory());

        Exchange exchange = consumer.receive("file:sampleOutput");
        System.out.println("Received body is :" + exchange.getIn().getBody());
        System.out.println("File Name is :" + exchange.getIn().getHeader("CamelFileName"));
        assertEquals("output.txt", exchange.getIn().getHeader("CamelFileName"));
    }
}