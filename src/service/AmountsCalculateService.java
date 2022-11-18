package service;

import model.InputData;
import model.Rate;
import model.RateAmounts;

public interface AmountsCalculateService {
    RateAmounts calculate(InputData inputData, Rate previousRate);
    RateAmounts calculate(InputData inputData);
}
