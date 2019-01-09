package pl.coderslab.warsztaty_7.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "incomes")
public class Income {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotEmpty
    private BigDecimal amount;

    @Column (name = "place_of_payment")
    private String placeOfPayment;

    @Column(name = "date_of_payment")
    @NotEmpty
    private LocalDateTime dateOfPayment;

    @Column(name = "income_note")
    private String incomeNote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "income_categories_id")
    private IncomeCategory incomeCategory;


    //TODO: pole na relację z kontem

    //TODO: pole na relację z userem

    //TODO: pole na relacje z budzetem? -> pytania do Marcina


    public Income() {
    }

    public Income(BigDecimal amount, String placeOfPayment, LocalDateTime dateOfPayment, String incomeNote) {
        this.amount = amount;
        this.placeOfPayment = placeOfPayment;
        this.dateOfPayment = dateOfPayment;
        this.incomeNote = incomeNote;
    }

    //TODO: Do usunięcia po podmianie repository z podłączeniem do DB
    public Income(Long id, BigDecimal amount, String placeOfPayment, String incomeNote, IncomeCategory incomeCategory) {
        this.id = id;
        this.amount = amount;
        this.placeOfPayment = placeOfPayment;
        this.dateOfPayment = LocalDateTime.now();
        this.incomeNote = incomeNote;
        this.incomeCategory = incomeCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPlaceOfPayment() {
        return placeOfPayment;
    }

    public void setPlaceOfPayment(String placeOfPayment) {
        this.placeOfPayment = placeOfPayment;
    }

    public LocalDateTime getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(LocalDateTime dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public String getIncomeNote() {
        return incomeNote;
    }

    public void setIncomeNote(String incomeNote) {
        this.incomeNote = incomeNote;
    }

    public IncomeCategory getIncomeCategory() {
        return incomeCategory;
    }

    public void setIncomeCategory(IncomeCategory incomeCategory) {
        this.incomeCategory = incomeCategory;
    }

    @Override
    public String toString() {
        return "Income{" +
                "id=" + id +
                ", amount=" + amount +
                ", placeOfPayment='" + placeOfPayment + '\'' +
                ", dateOfPayment=" + dateOfPayment +
                ", incomeNote='" + incomeNote + '\'' +
                ", incomeCategory=" + incomeCategory +
                '}';
    }
}
