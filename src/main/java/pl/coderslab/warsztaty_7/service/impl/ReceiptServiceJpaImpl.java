package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.warsztaty_7.model.*;
import pl.coderslab.warsztaty_7.repository.ReceiptRepository;
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
    private final ExpenseService expenseService;
    private final ReceiptUtil receiptUtil;

    @Autowired
    public ReceiptServiceJpaImpl(ReceiptRepository receiptRepository, ExpenseService expenseService, ReceiptUtil receiptUtil) {
        this.receiptRepository = receiptRepository;
        this.expenseService = expenseService;
        this.receiptUtil = receiptUtil;
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
        receiptUtil.adjustBankAccounts(receipt);
        receiptUtil.assignExpenses(receipt);
        return receiptRepository.save(receipt);
    }

    @Override
    public Receipt edit(Receipt receipt) {
        Receipt originalReceipt = receiptRepository.findOne(receipt.getId());
        receiptUtil.adjustBankAccounts(receipt, originalReceipt);
        receiptUtil.assignExpenses(receipt);
        List<Expense> originalExpenses = findById(receipt.getId()).getExpenses();
        List<Long> expensesToDelete = receiptUtil.findExpensesToDelete(receipt, originalExpenses);
        expenseService.deleteByIds(expensesToDelete); //TODO: to trzeba gdzieś przenieść
        return receiptRepository.save(receipt);
    }

    @Override
    public void deleteById(Long id) {
        Receipt selectedReceipt = receiptRepository.findOne(id);
        receiptUtil.adjustBankAccountForRemoval(selectedReceipt);
        receiptRepository.delete(id);
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
}
