package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.warsztaty_7.model.*;
import pl.coderslab.warsztaty_7.repository.ReceiptRepository;
import pl.coderslab.warsztaty_7.service.BankAccountService;
import pl.coderslab.warsztaty_7.service.ExpenseService;
import pl.coderslab.warsztaty_7.service.ReceiptService;
import pl.coderslab.warsztaty_7.util.ReceiptUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Primary
@Transactional
public class ReceiptServiceJpaImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final ReceiptUtil receiptUtil;
    private final BankAccountService bankAccountService;
    private final ExpenseService expenseService;

    @Autowired
    public ReceiptServiceJpaImpl(ReceiptRepository receiptRepository, ReceiptUtil receiptUtil, BankAccountService bankAccountService, ExpenseService expenseService) {
        this.receiptRepository = receiptRepository;
        this.receiptUtil = receiptUtil;
        this.bankAccountService = bankAccountService;
        this.expenseService = expenseService;
    }

    @Override
    public List<Receipt> findAllOrderedByDate() {
        return receiptRepository.findAllByOrderByDateOfPaymentDesc();
    }

    @Override
    public List<Receipt> findAllForBudgetOrderedByDate(Budget budget) {
        return receiptRepository.findAllByBankAccountBudgetOrderByDateOfPaymentDesc(budget);
    }

    @Override
    public Receipt findById(Long id) {
        return receiptRepository.findOne(id);
    }

    @Override
    public Receipt create(Receipt receipt) {
        adjustBankAccounts(receipt);
        assignExpenses(receipt);
        return receiptRepository.save(receipt);
    }

    private void adjustBankAccounts(Receipt receipt) {
        BankAccount selectedAccount = bankAccountService.findById(receipt.getBankAccount().getId());
        BigDecimal balanceBeforeTransaction = selectedAccount.getBalance();
        selectedAccount.setBalance(balanceBeforeTransaction.subtract(receipt.getAmount()));
    }

    private void assignExpenses(Receipt receipt) {
        List<Expense> currentExpenses = receipt.getExpenses();
        for (Expense expense : currentExpenses) {
            expense.setReceipt(receipt);
        }
    }

    @Override
    public Receipt edit(Receipt receipt) {
        Receipt originalReceipt = receiptRepository.findOne(receipt.getId());
        adjustBankAccounts(receipt, originalReceipt);
        assignExpenses(receipt);
        List<Expense> originalExpenses = findById(receipt.getId()).getExpenses();
        List<Long> expensesToDelete = findExpensesToDelete(receipt, originalExpenses);
        expenseService.deleteByIds(expensesToDelete);
        return receiptRepository.save(receipt);
    }

    private void adjustBankAccounts(Receipt receipt, Receipt originalReceipt) {
        BankAccount originalAccount = bankAccountService.findById(originalReceipt.getBankAccount().getId());
        BankAccount selectedAccount = bankAccountService.findById(receipt.getBankAccount().getId());
        BigDecimal selectedOldBalance = selectedAccount.getBalance();
        BigDecimal originalAmount = originalReceipt.getAmount();

        if (originalAccount.equals(selectedAccount)) {
            BigDecimal amountDiff = receipt.getAmount().subtract(originalAmount);
            selectedAccount.setBalance(selectedOldBalance.subtract(amountDiff));
        } else {
            BigDecimal originalOldBalance = originalAccount.getBalance();
            originalAccount.setBalance(originalOldBalance.add(originalAmount));
            selectedAccount.setBalance(selectedOldBalance.subtract(receipt.getAmount()));
        }
    }

    private List<Long> findExpensesToDelete(Receipt receipt, List<Expense> originalExpenses) {
        List<Long> expensesToDelete = new ArrayList<>();
        List<Expense> currentExpenses = receipt.getExpenses();

        for (Expense originalExpense : originalExpenses) {
            if (!currentExpenses.contains(originalExpense)) {
                expensesToDelete.add(originalExpense.getId());
            }
        }
        return expensesToDelete;
    }

    @Override
    public void deleteById(Long id) {
        Receipt selectedReceipt = receiptRepository.findOne(id);
        adjustBankAccountForRemoval(selectedReceipt);
        receiptRepository.delete(id);
    }

    private void adjustBankAccountForRemoval(Receipt receipt) {
        BankAccount selectedAccount = bankAccountService.findById(receipt.getBankAccount().getId());
        BigDecimal balanceBeforeRemoval = selectedAccount.getBalance();
        selectedAccount.setBalance(balanceBeforeRemoval.add(receipt.getAmount()));
    }

    @Override
    public Receipt findByExpenseId(Long id) {
        return null;
    }

    @Override
    public List<Receipt> findLast5ReceiptsForBudget(Budget budget) {
        Collection<Long> ids = new ArrayList<>();
        for (User user: budget.getUsers()) {
            ids.add(user.getId());
        }
        return receiptRepository.findTop5ReceiptsByCreatedByUserIdInOrderByDateOfPaymentDesc(ids);
    }

    @Override
    public BigDecimal sumAllFromThisMonth(Budget budget) {
        Collection<Long> ids = new ArrayList<>();
        for (User user: budget.getUsers()) {
            ids.add(user.getId());
        }
        LocalDate now = LocalDate.now();
        LocalDate begin = now.withDayOfMonth(1);
        LocalDate end = now.withDayOfMonth(now.lengthOfMonth());
        List<Receipt> receipts = receiptRepository.findAllByCreatedByUserIdInAndDateOfPaymentBetween(ids, begin, end);
        BigDecimal receiptsSumFromThisMonth = BigDecimal.ZERO;

        for (Receipt receipt : receipts){
            receiptsSumFromThisMonth = receiptsSumFromThisMonth.add(receipt.getAmount());
        }
        return receiptsSumFromThisMonth;
    }

    @Override
    public Expense createNewExpense(Receipt receipt) {
        return receiptUtil.createNewExpense(receipt);
    }

    @Override
    public boolean validateExpensesAmount(Receipt receipt) {
        return receiptUtil.validateExpensesAmount(receipt);
    }
}
