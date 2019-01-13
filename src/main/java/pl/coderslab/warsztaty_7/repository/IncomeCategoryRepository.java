package pl.coderslab.warsztaty_7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.warsztaty_7.model.IncomeCategory;

public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory, Long> {

    //TODO: zapytanie zwracające listę kategorii dla użytkownika (wszystkie globalne + wszystkie utworzone przez użytkowników z danego budżetu)
}
