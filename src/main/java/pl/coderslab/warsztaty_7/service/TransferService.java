package pl.coderslab.warsztaty_7.service;

import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.Transfer;

import java.util.List;

public interface TransferService {

    List<Transfer> findAllForBudgetOrderedByDate(Budget budget);
    Transfer findById(Long id);
    Transfer create(Transfer transfer);
    Transfer edit(Transfer transfer);
    void deleteById(Long id);
}
