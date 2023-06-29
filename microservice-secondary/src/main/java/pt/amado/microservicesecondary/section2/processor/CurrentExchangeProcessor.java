package pt.amado.microservicesecondary.section2.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pt.amado.microservicesecondary.section2.model.CurrentExchange;

@Slf4j
@Component
public class CurrentExchangeProcessor {

    public void processMessage(CurrentExchange currentExchange){
        log.info("Processing message. Do something with current exchange object: {}", currentExchange);
    }

}
