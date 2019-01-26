package pl.coderslab.warsztaty_7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.Income;
import pl.coderslab.warsztaty_7.model.IncomeCategory;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findAllByIncomeCategory(IncomeCategory incomeCategory);
    List<Income> findAllByIncomeCategoryId(Long id);
    List<Income> findAllByBankAccountBudgetOrderByDateOfPaymentDesc(Budget budget);

    List<Income> findAllByCreatedByUserIdInAndDateOfPaymentBetween(Collection<Long> userIds, LocalDate begin, LocalDate end);
}

