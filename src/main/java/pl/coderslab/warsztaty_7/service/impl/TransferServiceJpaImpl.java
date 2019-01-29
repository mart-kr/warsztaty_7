package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.warsztaty_7.model.BankAccount;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.Transfer;
import pl.coderslab.warsztaty_7.repository.TransferRepository;
import pl.coderslab.warsztaty_7.service.BankAccountService;
import pl.coderslab.warsztaty_7.service.TransferService;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class TransferServiceJpaImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final BankAccountService bankAccountService;

    @Autowired
    public TransferServiceJpaImpl(TransferRepository transferRepository, BankAccountService bankAccountService) {
        this.transferRepository = transferRepository;
        this.bankAccountService = bankAccountService;
    }

    @Override
    public List<Transfer> findAllForBudgetOrderedByDate(Budget budget) {
        return transferRepository.findAllByToAccountBudgetOrderByDateOfTransferDesc(budget);
    }

    @Override
    public Transfer findById(Long id) {
        return transferRepository.findOne(id);
    }

    @Override
    public Transfer create(Transfer transfer) {
        adjustBankAccounts(transfer);
        return transferRepository.save(transfer);
    }

    private void adjustBankAccounts(Transfer transfer) {
        BankAccount fromAccount = bankAccountService.findById(transfer.getFromAccount().getId());
        BigDecimal fromAccountBalance = fromAccount.getBalance();
        fromAccount.setBalance(fromAccountBalance.subtract(transfer.getAmount()));

        BankAccount toAccount = bankAccountService.findById(transfer.getToAccount().getId());
        BigDecimal toAccountBalance = toAccount.getBalance();
        toAccount.setBalance(toAccountBalance.add(transfer.getAmount()));
    }

    @Override
    public Transfer edit(Transfer transfer) {
        Transfer originalTransfer = transferRepository.findOne(transfer.getId());
        adjustOldBankAccounts(originalTransfer);
        adjustBankAccounts(transfer);
        return transferRepository.save(transfer);
    }

    private void adjustOldBankAccounts(Transfer originalTransfer) {
        BigDecimal transferAmount = originalTransfer.getAmount();
        BankAccount originalFromAccount = originalTransfer.getFromAccount();
        BigDecimal originalFromAccountBalance = originalFromAccount.getBalance();
        originalFromAccount.setBalance(originalFromAccountBalance.add(transferAmount));
        BankAccount originalToAccount = originalTransfer.getToAccount();
        BigDecimal originalToAccountBalance = originalToAccount.getBalance();
        originalToAccount.setBalance(originalToAccountBalance.subtract(transferAmount));
    }

    @Override
    public void deleteById(Long id) {
        adjustOldBankAccounts(transferRepository.findOne(id));
        transferRepository.delete(id);
    }
}
