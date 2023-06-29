package pt.amado.microservicesecondary.section2.receiver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class CurrentExchange {

    private Long id;
    private String from;
    private String to;
    private BigDecimal conversionMultiple;

    @Override
    public String toString() {
        return String.format("CurrentExchange object: id:%s - from:%s - to:%s - conversionMultiple:%s",
                getId(), getFrom(), getTo(), getConversionMultiple());
    }
}
