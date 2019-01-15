package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.coderslab.warsztaty_7.repository.BankAccountRepository;

@Service
@Primary
public class BankAccountServiceJpaImpl {

    private BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountServiceJpaImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }
}
