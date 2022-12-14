import model.InputData;
import service.*;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        InputData inputData = new InputData()
                .withAmount(new BigDecimal("2903444"))
                .withMonthsDuration(BigDecimal.valueOf(100));

        PrintingService printingService = new PrintingServiceImp();
        RateCalculationService rateCalculationService = new RateCalculationServiceImp(
                new TimePointServiceImpl(),
                new AmountsCalculateServiceImpl(),
                new ResidualCalculateServiceImpl()
        );
        MortgageCalculationService mortgageCalculationService = new MortgageCalculationServiceImp(
                printingService,
                rateCalculationService,
                SummaryServiceFactory.create(rateCalculationService.calculate(inputData))
        );
        mortgageCalculationService.calculate(inputData);
    }



}