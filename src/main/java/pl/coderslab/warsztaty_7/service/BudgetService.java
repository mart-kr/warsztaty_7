package pl.coderslab.warsztaty_7.service;

import pl.coderslab.warsztaty_7.model.Budget;

import java.util.List;

public interface BudgetService {

    List<Budget> findAll();
    Budget findById(Long id);
    Budget create(Budget budget);
    Budget edit(Budget budget);
    void deleteById(Long id);
}
