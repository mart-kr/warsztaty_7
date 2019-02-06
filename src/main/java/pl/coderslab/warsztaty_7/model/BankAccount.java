package pl.coderslab.warsztaty_7.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private List<Receipt> withdrawals = new ArrayList<>();

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    private List<Income> deposits = new ArrayList<>();

    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL)
    private List<Transfer> fromTransfers = new ArrayList<>();

    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL)
    private List<Transfer> toTransfers = new ArrayList<>();

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

    public List<Transfer> getFromTransfers() {
        return fromTransfers;
    }

    public void setFromTransfers(List<Transfer> fromTransfers) {
        this.fromTransfers = fromTransfers;
    }

    public void addFromTransfer(Transfer transfer) {
        this.fromTransfers.add(transfer);
        transfer.setFromAccount(this);
    }

    public List<Transfer> getToTransfers() {
        return toTransfers;
    }

    public void setToTransfers(List<Transfer> toTransfers) {
        this.toTransfers = toTransfers;
    }

    public void addToTransfer(Transfer transfer) {
        this.toTransfers.add(transfer);
        transfer.setToAccount(this);
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankAccount)) return false;
        if (!super.equals(o)) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getBalance(), that.getBalance()) &&
                Objects.equals(getWithdrawals(), that.getWithdrawals()) &&
                Objects.equals(getDeposits(), that.getDeposits()) &&
                Objects.equals(getFromTransfers(), that.getFromTransfers()) &&
                Objects.equals(getToTransfers(), that.getToTransfers()) &&
                Objects.equals(getBudget().getId(), that.getBudget().getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getId(), getName(), getBalance(), getWithdrawals(), getDeposits(), getFromTransfers(), getToTransfers(), getBudget());
    }
}
