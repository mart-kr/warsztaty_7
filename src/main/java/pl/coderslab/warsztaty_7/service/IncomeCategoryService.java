package pl.coderslab.warsztaty_7.service;

import pl.coderslab.warsztaty_7.model.IncomeCategory;

import java.util.List;

public interface IncomeCategoryService {

    List<IncomeCategory> findAll();
    List<IncomeCategory> findAllForBudgetId(Long id);
    IncomeCategory findById(Long id);
    IncomeCategory create(IncomeCategory incomeCategory);
    IncomeCategory edit(IncomeCategory incomeCategory);
    void deleteById(Long id);
}
