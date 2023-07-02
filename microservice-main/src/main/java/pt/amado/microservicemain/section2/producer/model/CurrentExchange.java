package pt.amado.microservicemain.section2.producer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
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

    public void setConversionMultiple(BigDecimal conversionMultiple) {
        this.conversionMultiple = conversionMultiple;
    }
}
