package pl.coderslab.warsztaty_7.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.BankAccount;
import pl.coderslab.warsztaty_7.model.Income;
import pl.coderslab.warsztaty_7.model.IncomeCategory;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.repository.BankAccountRepository;
import pl.coderslab.warsztaty_7.repository.IncomeRepository;
import pl.coderslab.warsztaty_7.service.IncomeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Primary
@Transactional
public class IncomeServiceJpaImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public IncomeServiceJpaImpl(IncomeRepository incomeRepository, BankAccountRepository bankAccountRepository) {
        this.incomeRepository = incomeRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public List<Income> findAll() {
        return this.incomeRepository.findAll();
    }

    @Override
    public List<Income> findAllForBudgetOrderedByDate(Budget budget) {
        return incomeRepository.findAllByBankAccountBudgetOrderByDateOfPaymentDesc(budget);
    }

    @Override
    public List<Income> findByCategory(IncomeCategory incomeCategory) {
        return this.incomeRepository.findAllByIncomeCategory(incomeCategory);
    }

    @Override
    public List<Income> findByCategoryId(Long id) {
        return this.incomeRepository.findAllByIncomeCategoryId(id);
    }

    @Override
    public Income findById(Long id) {
        return this.incomeRepository.findOne(id);
    }

    @Override
    public Income create(Income income) {
        BankAccount selectedBankAccount = bankAccountRepository.findOne(income.getBankAccount().getId());
        BigDecimal balanceBeforeTransaction = selectedBankAccount.getBalance();
        selectedBankAccount.setBalance(balanceBeforeTransaction.add(income.getAmount())); //TODO: dodać obsługę nulli - ustalić jak chcemy rzucać wyjątki (dodatkowo do walidacji formularzy)
        return this.incomeRepository.save(income);
    }

    @Override
    public Income edit(Income income) {
        Income originalIncome = incomeRepository.findOne(income.getId());
        BankAccount originalBankAccount = bankAccountRepository.findOne(originalIncome.getBankAccount().getId());
        BankAccount selectedBankAccount = bankAccountRepository.findOne(income.getBankAccount().getId());
        BigDecimal selectedOldBalance = selectedBankAccount.getBalance();
        BigDecimal originalAmount = originalIncome.getAmount();

        if (originalBankAccount.equals(selectedBankAccount)) {
            BigDecimal amountDiff = income.getAmount().subtract(originalAmount);
            selectedBankAccount.setBalance(selectedOldBalance.add(amountDiff));
        } else {
            BigDecimal originalOldBalance = originalBankAccount.getBalance();
            originalBankAccount.setBalance(originalOldBalance.subtract(originalAmount));
            selectedBankAccount.setBalance(selectedOldBalance.add(income.getAmount()));
        }
        return this.incomeRepository.save(income);
    }

    //TODO: do zmiany
    @Override
    public void deleteById(Long id) {
        Income selectedIncome = incomeRepository.findOne(id);
        BankAccount selectedBankAccount = bankAccountRepository.findOne(selectedIncome.getBankAccount().getId());
        BigDecimal balanceBeforeRemoval = selectedBankAccount.getBalance();
        selectedBankAccount.setBalance(balanceBeforeRemoval.subtract(selectedIncome.getAmount()));
        this.incomeRepository.delete(id);
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
        List<Income> incomes = incomeRepository.findAllByCreatedByUserIdInAndDateOfPaymentBetween(ids, begin, end);
        BigDecimal incomesSumFromThisMonth = BigDecimal.ZERO;

        for (Income income : incomes){
            incomesSumFromThisMonth = incomesSumFromThisMonth.add(income.getAmount());
        }

        return incomesSumFromThisMonth;
    }
}
