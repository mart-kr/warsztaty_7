package pl.coderslab.warsztaty_7.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.coderslab.warsztaty_7.model.BankAccount;
import pl.coderslab.warsztaty_7.model.Expense;
import pl.coderslab.warsztaty_7.model.Receipt;
import pl.coderslab.warsztaty_7.repository.BankAccountRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReceiptUtil { //TODO: czy to jest dobre miejsce dla tych metod?

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public ReceiptUtil(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<Long> findExpensesToDelete(Receipt receipt, List<Expense> originalExpenses) {
        List<Long> expensesToDelete = new ArrayList<>();
        List<Expense> currentExpenses = receipt.getExpenses();

        for (Expense originalExpense : originalExpenses) {
            if (!currentExpenses.contains(originalExpense)) {
                expensesToDelete.add(originalExpense.getId());
            }
        }
        return expensesToDelete;
    }

    public void assignExpenses(Receipt receipt) {
        List<Expense> currentExpenses = receipt.getExpenses();
        for (Expense expense : currentExpenses) {
            expense.setReceipt(receipt);
        }
    }

    public void adjustBankAccounts(Receipt receipt) {
        BankAccount selectedBankAccount = bankAccountRepository.findOne(receipt.getBankAccount().getId());
        BigDecimal balanceBeforeTransaction = selectedBankAccount.getBalance();
        selectedBankAccount.setBalance(balanceBeforeTransaction.subtract(receipt.getAmount()));
    }

    public void adjustBankAccounts(Receipt receipt, Receipt originalReceipt) {
        BankAccount originalBankAccount = bankAccountRepository.findOne(originalReceipt.getBankAccount().getId());
        BankAccount selectedBankAccount = bankAccountRepository.findOne(receipt.getBankAccount().getId());
        BigDecimal selectedOldBalance = selectedBankAccount.getBalance();
        BigDecimal originalAmount = originalReceipt.getAmount();

        if (originalBankAccount.equals(selectedBankAccount)) {
            BigDecimal amountDiff = receipt.getAmount().subtract(originalAmount);
            selectedBankAccount.setBalance(selectedOldBalance.subtract(amountDiff));
        } else {
            BigDecimal originalOldBalance = originalBankAccount.getBalance();
            originalBankAccount.setBalance(originalOldBalance.add(originalAmount));
            selectedBankAccount.setBalance(selectedOldBalance.subtract(receipt.getAmount()));
        }
    }

    public void adjustBankAccountForRemoval(Receipt receipt) {
        BankAccount selectedBankAccount = bankAccountRepository.findOne(receipt.getBankAccount().getId());
        BigDecimal balanceBeforeRemoval = selectedBankAccount.getBalance();
        selectedBankAccount.setBalance(balanceBeforeRemoval.add(receipt.getAmount()));
    }
}
