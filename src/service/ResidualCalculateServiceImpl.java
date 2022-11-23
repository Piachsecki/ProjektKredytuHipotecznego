package service;

import model.InputData;
import model.MortgageResidual;
import model.Rate;
import model.RateAmounts;

import java.math.BigDecimal;

public class ResidualCalculateServiceImpl implements ResidualCalculateService{




    @Override
    public MortgageResidual calculate(RateAmounts rateAmounts, InputData inputData) {
        BigDecimal residualAmount = inputData.getAmount().subtract(rateAmounts.getCapitalAmount()) ;
        BigDecimal residualDuration = inputData.getMonthsDuration().subtract(BigDecimal.ONE).max(BigDecimal.ZERO);
        return new MortgageResidual(residualAmount, residualDuration);
    }

    @Override
    public MortgageResidual calculate(RateAmounts rateAmounts, Rate previousRate) {
        BigDecimal residualAmount = previousRate.getMortgageResidual().getAmount().subtract(rateAmounts.getCapitalAmount());
        BigDecimal residualDuration = previousRate.getMortgageResidual().getDuration().subtract(BigDecimal.ONE);
        return new MortgageResidual(residualAmount,residualDuration);
    }
}
