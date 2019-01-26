package pl.coderslab.warsztaty_7.controller.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.ExpenseCategory;
import pl.coderslab.warsztaty_7.model.IncomeCategory;
import pl.coderslab.warsztaty_7.model.Target;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.service.ExpenseCategoryService;
import pl.coderslab.warsztaty_7.service.TargetService;

import java.util.List;

@Controller
@RequestMapping(value = "/home/target")
public class TestTargetController {

    private final TargetService targetService;
    private final ExpenseCategoryService expenseCategoryService;

    @Autowired
    public TestTargetController(TargetService targetService, ExpenseCategoryService expenseCategoryService) {
        this.targetService = targetService;
        this.expenseCategoryService = expenseCategoryService;
    }

    @ModelAttribute(name = "target")
    public Target createEmptyTarget(){
        return new Target();
    }

    @ModelAttribute(name = "expenseCategories")
    public List<ExpenseCategory> findAllCategories(){
        return expenseCategoryService.findAll();
    }

    @GetMapping(value = "/all")
    public String allTargets (@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("targets", targetService.findAll());
        return "test_targets";
    }

    @GetMapping(value = "/add")
    public String showCreateTargetForm(Model model) {
        model.addAttribute("action", "/home/target/add");
        return "test_targetForm";
    }

    @PostMapping(value = "/add")
    public String createTarget(@ModelAttribute Target target) {
        targetService.create(target);
        return "redirect:/home";
    }

    @GetMapping(value = "/edit/{id}")
    public String showEditTargetForm(@PathVariable Long id, Model model) {
        model.addAttribute("action", "/home/target/edit/" + id);
        model.addAttribute("target", targetService.findById(id));
        return "test_targetForm";
    }

    @PostMapping(value = "/edit/{id}")
    public String editTarget(@PathVariable Long id, @ModelAttribute Target target) {
        targetService.edit(target);
        return "redirect:/home";
    }

    @GetMapping(value = "delete/{id}")
    public String deleteTarget(@PathVariable Long id) {
        targetService.deleteById(id);
        return "redirect:/home";
    }
}
