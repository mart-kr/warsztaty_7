package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.warsztaty_7.repository.ExpenseRepository;
import pl.coderslab.warsztaty_7.repository.ReceiptRepository;
import pl.coderslab.warsztaty_7.service.CashflowService;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Transactional
public class CashflowServiceJpaImpl implements CashflowService {


    private ReceiptRepository receiptRepository;
    private ExpenseRepository expenseRepository;

    @Autowired
    public CashflowServiceJpaImpl(ReceiptRepository receiptRepository, ExpenseRepository expenseRepository) {
        this.receiptRepository = receiptRepository;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Integer incomePercent(BigDecimal incomeValue, BigDecimal receiptValue) {
        BigDecimal zero = BigDecimal.ZERO;
        if (receiptValue.equals(zero) && incomeValue.equals(zero)){
            return zero.intValue();
        } else{
            int value = bigDecimalComparison(incomeValue, receiptValue);
            return getInteger(incomeValue, receiptValue, value);
        }
    }

    @Override
    public Integer receiptPercent(BigDecimal receiptValue, BigDecimal incomeValue) {
        BigDecimal zero = BigDecimal.ZERO;
        if (receiptValue.equals(zero) && incomeValue.equals(zero)){
            return zero.intValue();
        } else{
            int value = bigDecimalComparison(receiptValue, incomeValue);
            return getInteger(receiptValue, incomeValue, value);
        }

    }

    @Override
    public BigDecimal balanceCashflow(BigDecimal receiptValue, BigDecimal incomeValue) {
        return incomeValue.subtract(receiptValue);
    }

    @Override
    public Integer bigDecimalComparison(BigDecimal firstBigDecimal, BigDecimal secondBigDecimal) {
        return firstBigDecimal.compareTo(secondBigDecimal);
    }

    @Override
    public Integer getInteger(BigDecimal firstValue, BigDecimal secondValue, int value) {
        Integer maxPercentage = 100;
        BigDecimal bd100 = new BigDecimal(100);

        if (value == 0){
            return maxPercentage;
        } else if (value == -1){
            return firstValue.multiply(bd100).divide(secondValue, RoundingMode.HALF_UP).intValue();
        } else {
            return maxPercentage;
        }
    }
}

