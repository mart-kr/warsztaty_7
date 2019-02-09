package pl.coderslab.warsztaty_7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.warsztaty_7.model.Budget;
import pl.coderslab.warsztaty_7.model.GlobalTarget;

import java.util.List;

@Repository
public interface GlobalTargetRepository extends JpaRepository<GlobalTarget, Long> {

    List<GlobalTarget> findAllByBudget(Budget budget);

    @Query("SELECT g FROM GlobalTarget g WHERE g.budget = :budget AND g.startDate < curdate() AND g.endDate > curdate() OR g.budget = :budget AND g.startDate < curdate() AND g.endDate IS NULL")
    List<GlobalTarget> findGlobalTargetForThisMonth(@Param(value = "budget") Budget budget);


}
