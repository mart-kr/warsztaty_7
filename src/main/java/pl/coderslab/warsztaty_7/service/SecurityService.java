package pl.coderslab.warsztaty_7.service;

import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.warsztaty_7.model.Auditable;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.User;

@Transactional
public interface SecurityService <U extends User, T extends Auditable> {

    boolean canViewOrEditEntity(U authenticatedUser, T entity);

    boolean canDeleteEntity(U authenticatedUser, T entity);

    boolean canDeleteBudget(U authenticatedUser, Budget budget);

    Budget findEntityBudget(T entity);
}