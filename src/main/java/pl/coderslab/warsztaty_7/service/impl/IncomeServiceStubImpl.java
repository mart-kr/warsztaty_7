package pl.coderslab.warsztaty_7.service.impl;

import org.springframework.stereotype.Service;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.Income;
import pl.coderslab.warsztaty_7.model.IncomeCategory;
import pl.coderslab.warsztaty_7.service.IncomeService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class IncomeServiceStubImpl implements IncomeService {

    private List<Income> incomes = new ArrayList<Income>() {{
        add(new Income(1L, new BigDecimal(100),"Biedra", "Notatka 1",
                new IncomeCategory(1L, "kat1", true)));
        add(new Income(2L, new BigDecimal(50.3),"Carrefour", "Notatka 2",
                new IncomeCategory(2L, "kat2", true)));
        add(new Income(3L, new BigDecimal(100),"Top Market", "Notatka 3",
                new IncomeCategory(3L, "kat3", true)));
    }};

    @Override
    public List<Income> findAllForBudgetOrderedByDate(Budget budget) {
        return null;
    }

    private List<IncomeCategory> incomeCategories = new ArrayList<IncomeCategory>() {{
            add(new IncomeCategory(4L, "kat4", true));
            add(new IncomeCategory(5L, "kat5", true));
            add(new IncomeCategory(6L, "kat6", true));
    }};


    @Override
    public List<Income> findAll() {
        return this.incomes;
    }

//    @Override
//    public List<IncomeCategory> findAllCategories() {
//        return this.incomeCategories;
//    }

    @Override
    public List<Income> findByCategory(IncomeCategory incomeCategory) {
        return this.incomes.stream()
                .filter(p -> p.getIncomeCategory().equals(incomeCategory))
                .collect((Collectors.toList()));
    }

//    @Override
//    public IncomeCategory findCategoryById(Long id) {
//        return this.incomeCategories.stream()
//                .filter(p -> p.getId().equals(id))
//                .findFirst()
//                .orElse(null);
//    }

    @Override
    public List<Income> findByCategoryId(Long id) {
        return this.incomes.stream()
                .filter(p -> p.getIncomeCategory().getId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    public Income findById(Long id) {
        return this.incomes.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Income create(Income income) {
        income.setId(this.incomes.stream().mapToLong(p -> p.getId()).max().getAsLong()+1);
        this.incomes.add(income);
        return income;
    }

    @Override
    public Income edit(Income income) {
        for (int i = 0; i <this.incomes.size(); i++){
            if (Objects.equals(this.incomes.get(i).getId(), income.getId())){
                this.incomes.set(i, income);
                return income;
            }
        }
        throw new RuntimeException("Income not fund: " + income.getId());
    }

    @Override
    public void deleteById(Long id) {
        for (int i = 0; i < this.incomes.size(); i++) {
            if (Objects.equals(this.incomes.get(i).getId(), id)) {
                this.incomes.remove(i);
                return;
            }
        }
        throw new RuntimeException("Income not fund: " + id);
    }

    @Override
    public BigDecimal sumAllFromThisMonth(Budget budget) {
        return null;
    }


}
