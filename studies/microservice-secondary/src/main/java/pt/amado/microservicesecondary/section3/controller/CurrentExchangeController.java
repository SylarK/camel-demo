package pt.amado.microservicesecondary.section3.controller;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pt.amado.microservicesecondary.section2.model.CurrentExchange;

import java.math.BigDecimal;

@RestController
@ConditionalOnProperty(
        value = "section.enabled",
        havingValue = "3",
        matchIfMissing = true
)
public class CurrentExchangeController {

    @GetMapping("/api/currency-exchange/from/{from}/to/{to}")
    public ResponseEntity<CurrentExchange> getConversionValue(@PathVariable String from, @PathVariable String to){
        return ResponseEntity.ok(new CurrentExchange(1L, from, to, BigDecimal.TEN));
    }

}
