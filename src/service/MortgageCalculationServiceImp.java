package service;

import model.InputData;
import model.Rate;
import model.Summary;

import java.util.List;

public class MortgageCalculationServiceImp implements MortgageCalculationService {
    private final PrintingService printingService;

    private final RateCalculationService rateCalculationService;

    private final SummaryService summaryService;

    public MortgageCalculationServiceImp(
            PrintingService printingService,
            RateCalculationService rateCalculationService,
            SummaryService summaryService
    ) {
        this.printingService = printingService;
        this.rateCalculationService = rateCalculationService;
        this.summaryService = summaryService;
    }

    @Override
    public void calculate(InputData inputData) {
        printingService.printInputDateInfo(inputData);
        List<Rate> rates = rateCalculationService.calculate(inputData);

        Summary summary = summaryService.calculate(rates);
        printingService.printSummary(summary);

        printingService.printRates(rates);
    }
}
