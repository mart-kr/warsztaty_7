package pl.coderslab.warsztaty_7.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "income_categories")
public class IncomeCategory {


    @Id
    @GeneratedValue
    @Column(name = "income_category_id")
    private Long id;

    @Column(name = "income_category_name", nullable = false)
    private String name;

    //pole na relacje z userem/budzetem?

//    @OneToMany(
//            mappedBy = "incomeCategory",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private List<Income> incomes = new ArrayList<>();


    public IncomeCategory() {
    }

    public IncomeCategory(String name) {
        this.name = name;
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
}
