package pt.amado.microservicemain.section1.routes.a;

import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@ConditionalOnProperty(
        value = "section.enabled",
        havingValue = "1",
        matchIfMissing = true
)
@RequiredArgsConstructor
public class MyFirstTimerRouter extends RouteBuilder {

    private final GetCurrentTimeBean getCurrentTimeBean;
    private final SimpleLoggingBean simpleLoggingBean;


    @Override
    public void configure() throws Exception {
        from("timer:first-timer")
                .log("${body}")
                .transform().constant("Constant message")
                .log("${body}")
                .bean(getCurrentTimeBean)
                .bean(simpleLoggingBean)
                .process(new SimpleProcessor())
                .to("log:first-timer");
    }

    @Component
    class GetCurrentTimeBean {
        public String getCurrentTime(){
            return String.format("Time now is %s", LocalDateTime.now());
        }
    }

    @Component
    class SimpleLoggingBean {

        private Logger log = LoggerFactory.getLogger(SimpleLoggingBean.class);

        public void process(String message) {
            log.info("SimpleLoggingProcessing --> {}", message);
        }

    }

    class SimpleProcessor implements Processor {

        private Logger log = LoggerFactory.getLogger(SimpleProcessor.class);

        @Override
        public void process(Exchange exchange) throws Exception {
            log.info("Body message : {}", exchange.getMessage().getBody());
        }

    }

}
