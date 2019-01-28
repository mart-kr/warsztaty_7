package pl.coderslab.warsztaty_7.service;


import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.Operation;

import java.util.List;

@Transactional
public interface OperationsService {

    List<Operation> loadAllOperationsForBudget(Budget budget);
}
