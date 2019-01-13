package pl.coderslab.warsztaty_7.service;

import pl.coderslab.warsztaty_7.model.Income;
import pl.coderslab.warsztaty_7.model.IncomeCategory;

import java.util.List;

public interface IncomeService {

    List<Income> findAll();
    List<Income> findByCategory(IncomeCategory incomeCategory);
    List<Income> findByCategoryId(Long id);
    Income findById(Long id);
    Income create(Income income);
    Income edit(Income income);
    void deleteById(Long id);

    //Do usunięcia/przeniesienia do oddzielnego interfejsu
//    List<IncomeCategory> findAllCategories();
//    IncomeCategory findCategoryById(Long id);
}
