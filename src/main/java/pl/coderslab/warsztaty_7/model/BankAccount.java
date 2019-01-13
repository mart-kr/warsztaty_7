package pl.coderslab.warsztaty_7.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "bank_accounts")
public class BankAccount extends Auditable {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private BigDecimal balance;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    private List<Receipt> withdrawals;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    private List<Income> deposits;

    @ManyToOne
    @JoinColumn(name = "budget_id")
    private Budget budget;

    public BankAccount() {}

    public BankAccount(String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Receipt> getWithdrawals() {
        return withdrawals;
    }

    public void setWithdrawals(List<Receipt> withdrawals) {
        this.withdrawals = withdrawals;
    }

    public void addWithdrawal(Receipt receipt) {
        this.withdrawals.add(receipt);
        receipt.setBankAccount(this);
    }

    public List<Income> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<Income> deposits) {
        this.deposits = deposits;
    }

    public void addDeposit(Income income) {
        this.deposits.add(income);
        income.setBankAccount(this);
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }
}
