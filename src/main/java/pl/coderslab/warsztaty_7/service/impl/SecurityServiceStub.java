package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.stereotype.Service;
import pl.coderslab.warsztaty_7.model.Auditable;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.service.SecurityService;

@Service
public class SecurityServiceStub implements SecurityService <User, Auditable> {

    @Override
    public boolean canViewOrEditEntity(User authenticatedUser, Auditable entity) {
        return true;
    }

    @Override
    public boolean canDeleteEntity(User authenticatedUser, Auditable entity) {
        return true;
    }

    @Override
    public boolean canDeleteBudget(User authenticatedUser, Budget budget) {
        return true;
    }

    @Override
    public Budget findEntityBudget(Auditable entity) {
        return null;
    }
}
