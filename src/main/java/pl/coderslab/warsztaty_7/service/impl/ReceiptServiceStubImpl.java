package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.stereotype.Service;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.Expense;
import pl.coderslab.warsztaty_7.model.Receipt;
import pl.coderslab.warsztaty_7.service.ReceiptService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReceiptServiceStubImpl implements ReceiptService {

    private List<Receipt> receipts = new ArrayList<Receipt>() {{
       add(new Receipt(1L, new BigDecimal(230), LocalDate.parse("2019-01-01"), "sklep1", "notatka1"));
       add(new Receipt(2L, new BigDecimal(290), LocalDate.parse("2019-01-10"), "sklep2", "notatka2"));
       add(new Receipt(3L, new BigDecimal(130), LocalDate.parse("2019-01-04"), "sklep3", null));
    }};

    @Override
    public List<Receipt> findAllOrderedByDate() {
        return this.receipts.stream()
                .sorted(Comparator.comparing(Receipt::getDateOfPayment).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Receipt> findAllForBudgetOrderedByDate(Budget budget) {
        return null;
    }

    @Override
    public Receipt findById(Long id) {
        return this.receipts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    //TODO: 1. jak dodawać expensy do receipta w jednym formularzu?
    //TODO: czy tutaj też powinniśmy pobrać listę expensów z obiektu i dodać ją do listy expensów w pamięci?
    @Override
    public Receipt create(Receipt receipt) {
        receipt.setId(this.receipts.stream().mapToLong(
                p -> p.getId()).max().getAsLong() + 1);
        this.receipts.add(receipt);
        return receipt;
    }

    @Override
    public Receipt edit(Receipt receipt) {
        for (int i = 0; i < receipts.size(); i++) {
            if (Objects.equals(this.receipts.get(i).getId(), receipt.getId())) {
                this.receipts.set(i, receipt);
                return receipt;
            }
        }
        throw new RuntimeException("Receipt not found: " + receipt.getId());
    }

    @Override
    public void deleteById(Long id) {
        for (int i = 0; i < this.receipts.size(); i++ ) {
            if (Objects.equals(this.receipts.get(i).getId(), id)) {
                this.receipts.remove(i);
                return;
            }
        }
        throw new RuntimeException("Receipt not fount: " + id);

    }

    //to będzie łatwiejsze do sprawdzenia w bazie
    @Override
    public Receipt findByExpenseId(Long id) {
        return null;
    }

    @Override
    public List<Receipt> findLast5ReceiptsForBudget(Budget budget) {
        return Collections.emptyList();
    }

    @Override
    public BigDecimal sumAllFromThisMonth(Budget budget) {
        return null;
    }

    @Override
    public Expense createNewExpense(Receipt receipt) {
        return null;
    }

    @Override
    public boolean validateExpensesAmount(Receipt receipt) {
        return false;
    }
}
