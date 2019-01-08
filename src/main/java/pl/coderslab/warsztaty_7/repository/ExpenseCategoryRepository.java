package pl.coderslab.warsztaty_7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;


public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {

    //TODO: zapytanie zwracające listę kategorii dla użytkownika (wszystkie globalne + wszystkie utworzone przez użytkowników z danego budżetu)
}
