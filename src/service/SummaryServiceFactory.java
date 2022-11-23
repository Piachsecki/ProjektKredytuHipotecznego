package service;

import model.Rate;
import model.Summary;

import java.math.BigDecimal;
import java.util.List;

public class SummaryServiceFactory {
    public static SummaryService create(List<Rate> rates) {
        return rates1 -> {
            BigDecimal interestSum = calculateInterestSum(rates);
            return new Summary(interestSum);
        };

    }

    private static BigDecimal calculateInterestSum(List<Rate> rates) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Rate rate : rates) {
            sum = sum.add(rate.getRateAmounts().getInterestAmount());
        }
        return sum;

    }

}
