package pl.coderslab.warsztaty_7.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.BankAccount;
import pl.coderslab.warsztaty_7.model.Income;
import pl.coderslab.warsztaty_7.model.IncomeCategory;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.repository.BankAccountRepository;
import pl.coderslab.warsztaty_7.repository.IncomeRepository;
import pl.coderslab.warsztaty_7.service.IncomeService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Primary
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
        BigDecimal balanceAfterTransaction = balanceBeforeTransaction.add(income.getAmount()); //TODO: dodać obsługę nulli - ustalić jak chcemy rzucać wyjątki (dodatkowo do walidacji formularzy)
        selectedBankAccount.setBalance(balanceAfterTransaction);
        return this.incomeRepository.save(income);
    }

    @Override
    public Income edit(Income income) {
        return this.incomeRepository.save(income);
    }

    //TODO: do zmiany
    @Override
    public void deleteById(Long id) {
        this.incomeRepository.delete(id);
    }

    @Override
    public BigDecimal sumAllFromThisMonth(Budget budget) {

        Collection<Long> ids = new ArrayList<>();
        for (User user: budget.getUsers()) {
            ids.add(user.getId());
        }
        List<Income> incomes = incomeRepository.findAllByThisMonth(ids);
        BigDecimal incomesSumFromThisMonth = BigDecimal.ZERO;

        for (Income income : incomes){
            incomesSumFromThisMonth = incomesSumFromThisMonth.add(income.getAmount());
        }

        return incomesSumFromThisMonth;
    }
}
