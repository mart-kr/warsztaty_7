package pl.coderslab.warsztaty_7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.Receipt;

import java.util.Collection;
import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    List<Receipt> findAllByOrderByDateOfPaymentDesc();
    List<Receipt> findAllByBankAccountBudgetOrderByDateOfPaymentDesc(Budget budget);

    List<Receipt> findTop5ReceiptsByCreatedByUserIdInOrderByDateOfPaymentDesc(Collection<Long> ids);

    @Query("select r from Receipt r where month(r.dateOfPayment) = month(current_date()) and year(r.dateOfPayment) = year(current_date()) and r.createdByUserId = ?1")
    List<Receipt> findAllByThisMonth(Collection<Long> ids);

    //TODO: wyszukiwanie po expense id - zastanowić się czy jest potrzebne
}
