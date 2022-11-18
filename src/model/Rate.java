package model;

import java.math.BigDecimal;

public class Rate {

    //numer raty
    private final BigDecimal rateNumber;

    //punkt w czasie
    private final TimePoint timePoint;

    @Override
    public String toString() {
        return "Rate{" +
                "rateNumber=" + rateNumber +
                ", timePoint=" + timePoint +
                ", rateAmounts=" + rateAmounts +
                ", mortgageResidual=" + mortgageResidual +
                '}';
    }

    //kwoty jakie beda tej raty dotyczyly
    private final RateAmounts rateAmounts;

    //wartosci pozostale po splaceniu tej raty
    private final MortgageResidual mortgageResidual;


    public BigDecimal getRateNumber() {
        return rateNumber;
    }

    public TimePoint getTimePoint() {
        return timePoint;
    }

    public RateAmounts getRateAmounts() {
        return rateAmounts;
    }

    public MortgageResidual getMortgageResidual() {
        return mortgageResidual;
    }

    public Rate(BigDecimal rateNumber, TimePoint timePoint, RateAmounts rateAmounts, MortgageResidual mortgageResidual) {
        this.rateNumber = rateNumber;
        this.timePoint = timePoint;
        this.rateAmounts = rateAmounts;
        this.mortgageResidual = mortgageResidual;
    }
}
