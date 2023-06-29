package pt.amado.cameldemo.routes.a;

import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MyFirstTimerRouter extends RouteBuilder {

    private final GetCurrentTimeBean getCurrentTimeBean;
    private final SimpleLoggingProcessingComponent simpleLoggingProcessingComponent;


    @Override
    public void configure() throws Exception {
        from("timer:first-timer")
                .log("${body}")
                .transform().constant("Constant message")
                .log("${body}")
                .bean(getCurrentTimeBean)
                .bean(simpleLoggingProcessingComponent)
                .to("log:first-timer");
    }

}

@Component
class GetCurrentTimeBean {
    public String getCurrentTime(){
        return String.format("Time now is %s", LocalDateTime.now());
    }
}

@Component
class SimpleLoggingProcessingComponent {

    private Logger log = LoggerFactory.getLogger(MyFirstTimerRouter.class);

    public void process(String message) {
        log.info("SimpleLoggingProcessing --> {}", message);
    }

}