package pl.coderslab.warsztaty_7.service;

import pl.coderslab.warsztaty_7.model.BankAccount;

import java.util.List;

public interface BankAccountService {

    List<BankAccount> findAll();
    List<BankAccount> findByBudgetId(Long id);
    BankAccount findById(Long id);
    BankAccount create(BankAccount bankAccount);
    BankAccount edit(BankAccount bankAccount);
    void deleteById(Long id);

}
