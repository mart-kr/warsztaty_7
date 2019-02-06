package pl.coderslab.warsztaty_7.util;

import java.math.BigDecimal;

public class TargetExpenseAmountsTransfer {

    private String categoryName;
    private BigDecimal targetAmount;
    private BigDecimal expenseAmount;
    private Integer targetPercentage;
    private Integer expensePercentage;

    public TargetExpenseAmountsTransfer(String categoryName, BigDecimal targetAmount, BigDecimal expenseAmount,
                                        Integer targetPercentage, Integer expensePercentage) {
        this.categoryName = categoryName;
        this.targetAmount = targetAmount;
        this.expenseAmount = expenseAmount;
        this.targetPercentage = targetPercentage;
        this.expensePercentage = expensePercentage;
    }

    public TargetExpenseAmountsTransfer() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public BigDecimal getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(BigDecimal expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public Integer getTargetPercentage() {
        return targetPercentage;
    }

    public void setTargetPercentage(Integer targetPercentage) {
        this.targetPercentage = targetPercentage;
    }

    public Integer getExpensePercentage() {
        return expensePercentage;
    }

    public void setExpensePercentage(Integer expensePercentage) {
        this.expensePercentage = expensePercentage;
    }
}
