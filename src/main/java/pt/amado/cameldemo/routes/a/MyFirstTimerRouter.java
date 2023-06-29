package pt.amado.cameldemo.routes.a;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyFirstTimerRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:first-timer")
                .transform().constant(String.format("Time now is %s", LocalDateTime.now()))
                .to("log:first-timer");
    }

}
