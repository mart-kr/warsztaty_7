package pl.coderslab.warsztaty_7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.warsztaty_7.model.BankAccount;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.Receipt;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    List<Receipt> findAllByOrderByDateOfPaymentDesc();
    List<Receipt> findAllByBankAccountBudgetOrderByDateOfPaymentDesc(Budget budget);
    List<Receipt> findTop5ReceiptsByCreatedByUserIdInOrderByDateOfPaymentDesc(Collection<Long> ids);
    List<Receipt> findAllByCreatedByUserIdInAndDateOfPaymentBetween
            (Collection<Long> userIds, LocalDate begin, LocalDate end);

    //TODO: wyszukiwanie po expense id - zastanowić się czy jest potrzebne
}
