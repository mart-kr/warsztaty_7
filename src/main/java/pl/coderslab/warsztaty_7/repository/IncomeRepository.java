package pl.coderslab.warsztaty_7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.Income;
import pl.coderslab.warsztaty_7.model.IncomeCategory;

import java.util.Collection;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findAllByIncomeCategory(IncomeCategory incomeCategory);
    List<Income> findAllByIncomeCategoryId(Long id);
    List<Income> findAllByBankAccountBudgetOrderByDateOfPaymentDesc(Budget budget);

    @Query("select i from Income i where month(i.dateOfPayment) = month(current_date()) and year(i.dateOfPayment) = year(current_date()) and i.createdByUserId = ?1")
    List<Income> findAllByThisMonth(Collection<Long> ids);
}

