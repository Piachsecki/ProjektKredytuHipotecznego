package service;

import model.InputData;
import model.Rate;
import model.RateAmounts;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmountsCalculateServiceImpl implements AmountsCalculateService {

    private static final BigDecimal YEAR = BigDecimal.valueOf(12);


    @Override
    public RateAmounts calculate(InputData inputData) {
        switch (inputData.getRateType()) {
            case CONSTANT:
                return calculateConstantRate(inputData);
            case DECREASING:
                return calculateDecreasingRate(inputData);
            default:
                throw new RuntimeException("This case is not handled");
        }

    }

    @Override
    public RateAmounts calculate(InputData inputData, Rate previousRate) {
        switch (inputData.getRateType()) {
            case CONSTANT:
                return calculateConstantRate(inputData, previousRate);
            case DECREASING:
                return calculateDecreasingRate(inputData, previousRate);
            default:
                throw new RuntimeException("This case is not handled");
        }
    }

    private RateAmounts calculateConstantRate(InputData inputData) {
        BigDecimal interestPercent = inputData.getInterestPercent();
        BigDecimal residualAmount = inputData.getAmount();

        BigDecimal q = calculateQ(interestPercent);

        BigDecimal rateAmount = calculateConstantRateAmount(q, inputData.getAmount(), inputData.getMonthsDuration());
        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal capitalAmount = calculateConstantCapitalAmount(rateAmount, interestAmount);
        return new RateAmounts(rateAmount, interestAmount, capitalAmount);
    }

    private RateAmounts calculateConstantRate(InputData inputData, Rate previousRate) {
        BigDecimal interestPercent = inputData.getInterestPercent();
        BigDecimal residualAmount = previousRate.getMortgageResidual().getAmount();

        BigDecimal q = calculateQ(interestPercent);

        BigDecimal rateAmount = calculateConstantRateAmount(q, inputData.getAmount(), inputData.getMonthsDuration());
        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal capitalAmount = calculateConstantCapitalAmount(rateAmount, interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount);
    }

    private RateAmounts calculateDecreasingRate(InputData inputData) {
        BigDecimal interestPercent = inputData.getInterestPercent();
        BigDecimal residualAmount = inputData.getAmount();

        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal capitalAmount = calculateDecreasingCapitalAmount(inputData.getAmount(), inputData.getMonthsDuration());
        BigDecimal rateAmount = capitalAmount.add(interestAmount);


        return new RateAmounts(rateAmount, interestAmount, capitalAmount);
    }

    private BigDecimal calculateDecreasingCapitalAmount(BigDecimal amount, BigDecimal monthsDuration) {
        return amount.divide(monthsDuration, 50, RoundingMode.HALF_UP);
    }

    private RateAmounts calculateDecreasingRate(InputData inputData, Rate previousRate) {
        BigDecimal interestPercent = inputData.getInterestPercent();
        BigDecimal residualAmount = previousRate.getMortgageResidual().getAmount();

        BigDecimal interestAmount = calculateInterestAmount(residualAmount, interestPercent);
        BigDecimal monthsDuration = inputData.getMonthsDuration();
        BigDecimal capitalAmount = calculateDecreasingCapitalAmount(inputData.getAmount(), monthsDuration);
        BigDecimal rateAmount = capitalAmount.add(interestAmount);

        return new RateAmounts(rateAmount, interestAmount, capitalAmount);
    }

    private BigDecimal calculateQ(BigDecimal interestPercent) {
        return interestPercent.divide(YEAR, 10,  RoundingMode.HALF_UP).add(BigDecimal.ONE);
    }

    private BigDecimal calculateConstantRateAmount(BigDecimal q, BigDecimal amount, BigDecimal monthsDuration) {
        return amount
                .multiply(q.pow(monthsDuration.intValue()))
                .multiply(q.subtract(BigDecimal.ONE))
                .divide(q.pow(monthsDuration.intValue()).subtract(BigDecimal.ONE), 50, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateConstantCapitalAmount(BigDecimal rateAmount, BigDecimal interestAmount) {
        return rateAmount.subtract(interestAmount);
    }

    private BigDecimal calculateInterestAmount(BigDecimal residualAmount, BigDecimal interestPercent) {

        return residualAmount.multiply(interestPercent).divide(YEAR, 50, RoundingMode.HALF_UP);
    }

}
