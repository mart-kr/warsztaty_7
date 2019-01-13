package pl.coderslab.warsztaty_7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.warsztaty_7.model.Income;
import pl.coderslab.warsztaty_7.model.IncomeCategory;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    public List<Income> findAllByIncomeCategory(IncomeCategory incomeCategory);
    public List<Income> findAllByIncomeCategoryId(Long id);
}
