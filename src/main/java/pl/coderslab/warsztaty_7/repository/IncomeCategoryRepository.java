package pl.coderslab.warsztaty_7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.warsztaty_7.model.IncomeCategory;

import java.util.List;

public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory, Long> {

    List<IncomeCategory> findAllByIsGlobalIsTrueOrBudgetIdOrderByName(Long id);
}