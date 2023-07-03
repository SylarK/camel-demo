package pt.amado.studies.cameljunit.route;

import lombok.SneakyThrows;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CopyFilesRouteTest extends CamelTestSupport {

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new CopyFilesRoute();
    }

    @Test
    @SneakyThrows
    public void shouldMoveFilesToOutputFolder() {
        Thread.sleep(5000);
        File file = new File("files/output");
        assertTrue(file.isDirectory());
        assertTrue(Objects.requireNonNull(file.listFiles()).length > 0);
    }
}