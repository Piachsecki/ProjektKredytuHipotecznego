package service;

import model.*;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class RateCalculationServiceImp implements RateCalculationService{
    private final TimePointService timePointService;

    private final AmountsCalculateService amountsCalculateService;

    private final ResidualCalculateService residualCalculateService;

    public RateCalculationServiceImp(TimePointService timePointService,
                                     AmountsCalculateService amountsCalculateService,
                                     ResidualCalculateService residualCalculateService
    ) {
        this.timePointService = timePointService;
        this.amountsCalculateService = amountsCalculateService;
        this.residualCalculateService = residualCalculateService;
    }

    @Override
    public List<Rate> calculate(InputData inputData) {
        List<Rate> rates = new LinkedList<>();

        BigDecimal rateNumber = BigDecimal.ONE;

        Rate firstRate = calculateRate(rateNumber, inputData);
        rates.add(firstRate);

        Rate previousRate = firstRate;

        for(
                BigDecimal index = rateNumber.add(BigDecimal.ONE);
                index.compareTo(inputData.getMonthsDuration()) <= 0;
                index = index.add(BigDecimal.ONE)
        ){
            Rate nextRate = calculateRate(index, inputData, previousRate);
            rates.add(nextRate);
            previousRate = nextRate;

        }

        return rates;
    }



    private Rate calculateRate(BigDecimal rateNumber, InputData inputData) {
        TimePoint timePoint = timePointService.calculate(rateNumber, inputData);
        RateAmounts rateAmounts = amountsCalculateService.calculate(inputData);
        MortgageResidual mortgageResidual = residualCalculateService.calculate(rateAmounts, inputData);
        return new Rate(rateNumber, timePoint, rateAmounts, mortgageResidual);
    }

    private Rate calculateRate(BigDecimal rateNumber, InputData inputData, Rate previousRate) {
        TimePoint timePoint = timePointService.calculate(rateNumber, inputData);
        RateAmounts rateAmounts = amountsCalculateService.calculate(inputData, previousRate);
        MortgageResidual mortgageResidual = residualCalculateService.calculate(rateAmounts, previousRate);
        return new Rate(rateNumber, timePoint, rateAmounts, mortgageResidual);
    }




}
