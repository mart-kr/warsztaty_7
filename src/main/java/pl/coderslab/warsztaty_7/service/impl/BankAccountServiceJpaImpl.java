package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.warsztaty_7.model.BankAccount;
import pl.coderslab.warsztaty_7.repository.BankAccountRepository;
import pl.coderslab.warsztaty_7.service.BankAccountService;

import java.util.List;

@Service
@Transactional
public class BankAccountServiceJpaImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountServiceJpaImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }


    @Override
    public List<BankAccount> findAll() {
        return bankAccountRepository.findAll();
    }

    @Override
    public List<BankAccount> findByBudgetId(Long id) {
        return bankAccountRepository.findAllByBudgetId(id);
    }

    @Override
    public BankAccount findById(Long id) {
        return bankAccountRepository.findOne(id);
    }

    @Override
    public BankAccount create(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public BankAccount edit(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public void deleteById(Long id) {
        bankAccountRepository.delete(id);
    }
}
