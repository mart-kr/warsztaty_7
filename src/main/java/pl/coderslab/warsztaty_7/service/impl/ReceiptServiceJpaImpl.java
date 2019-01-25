package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.coderslab.warsztaty_7.model.*;
import pl.coderslab.warsztaty_7.repository.BankAccountRepository;
import pl.coderslab.warsztaty_7.repository.ReceiptRepository;
import pl.coderslab.warsztaty_7.service.ReceiptService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Primary
@Transactional
public class ReceiptServiceJpaImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public ReceiptServiceJpaImpl(ReceiptRepository receiptRepository, BankAccountRepository bankAccountRepository) {
        this.receiptRepository = receiptRepository;
        this.bankAccountRepository = bankAccountRepository;
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
        BankAccount selectedBankAccount = bankAccountRepository.findOne(receipt.getBankAccount().getId());
        BigDecimal balanceBeforeTransaction = selectedBankAccount.getBalance();
        selectedBankAccount.setBalance(balanceBeforeTransaction.subtract(receipt.getAmount())); //TODO: dodać obsługę nulli - ustalić jak chcemy rzucać wyjątki (dodatkowo do walidacji formularzy)
        return receiptRepository.save(receipt);
    }

    @Override
    public Receipt edit(Receipt receipt) {
        Receipt originalReceipt = receiptRepository.findOne(receipt.getId());
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
        return receiptRepository.save(receipt);
    }

    @Override
    public void deleteById(Long id) {
        Receipt selectedReceipt = receiptRepository.findOne(id);
        BankAccount selectedBankAccount = bankAccountRepository.findOne(selectedReceipt.getBankAccount().getId());
        BigDecimal balanceBeforeRemoval = selectedBankAccount.getBalance();
        selectedBankAccount.setBalance(balanceBeforeRemoval.add(selectedReceipt.getAmount()));
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
        List<Receipt> receipts = receiptRepository.findAllByThisMonth(ids);
        BigDecimal receiptsSumFromThisMonth = BigDecimal.ZERO;

        for (Receipt receipt : receipts){
            receiptsSumFromThisMonth = receiptsSumFromThisMonth.add(receipt.getAmount());
        }

        return receiptsSumFromThisMonth;
    }
}
