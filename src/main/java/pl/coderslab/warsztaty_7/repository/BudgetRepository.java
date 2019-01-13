package pl.coderslab.warsztaty_7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.warsztaty_7.model.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
}
