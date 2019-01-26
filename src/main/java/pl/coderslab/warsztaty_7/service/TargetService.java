package pl.coderslab.warsztaty_7.service;

import pl.coderslab.warsztaty_7.model.ExpenseCategory;
import pl.coderslab.warsztaty_7.model.Target;

import java.util.List;

public interface TargetService {

    List<Target> findAll();
    List<Target> findByCategory(ExpenseCategory expenseCategory);
    List<Target> findByCategoryId(Long id);
    Target findById(Long id);
    Target create(Target target);
    Target edit(Target target);
    void deleteById(Long id);

}
