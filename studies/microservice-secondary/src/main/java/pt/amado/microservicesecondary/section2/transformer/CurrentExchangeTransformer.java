package pt.amado.microservicesecondary.section2.transformer;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import pt.amado.microservicesecondary.section2.model.CurrentExchange;

import java.math.BigDecimal;

@Component
@ConditionalOnProperty(
        value = "section.enabled",
        havingValue = "2",
        matchIfMissing = true
)
public class CurrentExchangeTransformer {

    public void transform(CurrentExchange currentExchange) {
        currentExchange.setConversionMultiple(currentExchange.getConversionMultiple()
                .pow(BigDecimal.valueOf(2).intValue()));
    }

}
