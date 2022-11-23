package model;

import java.math.BigDecimal;

public class Summary {
    private final BigDecimal interestSum;

    public BigDecimal getInterestSum() {
        return interestSum;
    }

    public Summary(BigDecimal interestSum) {
        this.interestSum = interestSum;
    }
}
