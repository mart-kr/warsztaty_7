package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.warsztaty_7.model.*;
import pl.coderslab.warsztaty_7.service.IncomeService;
import pl.coderslab.warsztaty_7.service.OperationsService;
import pl.coderslab.warsztaty_7.service.ReceiptService;
import pl.coderslab.warsztaty_7.service.TransferService;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperationsServiceJpaImpl implements OperationsService {

    private final ReceiptService receiptService;
    private final IncomeService incomeService;
    private final TransferService transferService;

    @Autowired
    public OperationsServiceJpaImpl(ReceiptService receiptService, IncomeService incomeService, TransferService transferService) {
        this.receiptService = receiptService;
        this.incomeService = incomeService;
        this.transferService = transferService;
    }

    @Override
    public List<Operation> loadAllOperationsForBudget(Budget budget) {
        List<Operation> allOperations = new ArrayList<>(receiptService.findAllForBudgetOrderedByDate(budget));
        allOperations.addAll(incomeService.findAllForBudgetOrderedByDate(budget));
        allOperations.addAll(transferService.findAllForBudgetOrderedByDate(budget));
        allOperations.sort((op1, op2)-> op2.getOperationDate().compareTo(op1.getOperationDate()));
        return allOperations;
    }
}
