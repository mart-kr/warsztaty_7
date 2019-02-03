package pl.coderslab.warsztaty_7.service;

import java.math.BigDecimal;

public interface CashflowService {

    Integer incomePercent(BigDecimal incomeValue, BigDecimal receiptValue);
    Integer receiptPercent(BigDecimal receiptValue, BigDecimal incomeValue);
    BigDecimal balanceCashflow(BigDecimal receiptValue, BigDecimal incomeValue);
    Integer bigDecimalComparison(BigDecimal firstBigDecimal, BigDecimal secondBigDecimal);
    Integer getInteger(BigDecimal firstValue, BigDecimal secondValue, int value);

}
