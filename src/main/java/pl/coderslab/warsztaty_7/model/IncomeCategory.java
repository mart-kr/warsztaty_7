package pl.coderslab.warsztaty_7.model;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "income_categories")
public class IncomeCategory extends Auditable {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotEmpty
    @Size(max = 100)
    private String name;

    @Column(name = "is_global")
    private boolean isGlobal;

    @OneToMany(mappedBy = "incomeCategory", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Income> incomeList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "budget_id")
    private Budget budget;

    public IncomeCategory() {
    }

    public IncomeCategory(String name, boolean isGlobal) {
        this.name = name;
        this.isGlobal = isGlobal;
    }

    //TODO: Do usunięcia po podmianie repository z podłączeniem do DB
    public IncomeCategory(Long id, String name, boolean isGlobal) {
        this.id = id;
        this.name = name;
        this.isGlobal = isGlobal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public void setGlobal(boolean global) {
        isGlobal = global;
    }

    public List<Income> getIncomeList() {
        return incomeList;
    }

    public void setIncomeList(List<Income> incomeList) {
        this.incomeList = incomeList;
    }

    public void addIncome(Income income){
        incomeList.add(income);
        income.setIncomeCategory(this);
    }

    public void deleteIncome(Income income){
        incomeList.remove(income);
        income.setIncomeCategory(null); //ToDO:zmienić usuwanie na zmianę flagi
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "IncomeCategory{" +
                "id=" + id +
                ", name='" + name +
                ", isGlobal=" + isGlobal +
                ", incomeList=" + incomeList +
                ", createdByUserId=" + createdByUserId +
                ", creationDate=" + creationDate +
                ", updatedByUserId=" + updatedByUserId +
                ", updatedDate=" + updatedDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IncomeCategory)) return false;
        if (!super.equals(o)) return false;
        IncomeCategory that = (IncomeCategory) o;
        return isGlobal() == that.isGlobal() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getIncomeList(), that.getIncomeList()) &&
                Objects.equals(getBudget().getId(), that.getBudget().getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getId(), getName(), isGlobal(), getIncomeList(), getBudget());
    }
}
