package pl.coderslab.warsztaty_7.service;

import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.warsztaty_7.model.Auditable;
import pl.coderslab.warsztaty_7.model.Budget;

@Transactional
public interface SecurityService <U, T extends Auditable> {

    boolean canEditEntity(U authenticatedUser, T entity);

    boolean canDeleteEntity(U authenticatedUser, T entity);

    boolean canDeleteBudget(U authenticatedUser, T budget);

    Budget findEntityBudget(T entity);
}