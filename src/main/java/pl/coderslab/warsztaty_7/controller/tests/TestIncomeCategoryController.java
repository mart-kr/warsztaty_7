package pl.coderslab.warsztaty_7.controller.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.IncomeCategory;
import pl.coderslab.warsztaty_7.model.User;
import pl.coderslab.warsztaty_7.service.IncomeCategoryService;

@Controller
@RequestMapping(value = "home/incomeCategory")
public class TestIncomeCategoryController {

    private final IncomeCategoryService incomeCategoryService;

    @Autowired
    public TestIncomeCategoryController(IncomeCategoryService incomeCategoryService){
        this.incomeCategoryService = incomeCategoryService;
    }

    @ModelAttribute
    private IncomeCategory createEmptyIncomeCategory(){
        return new IncomeCategory();
    }

    @GetMapping(value = "/all")
    public String allIncomeCategories(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("incomeCategories", incomeCategoryService.findAllForBudgetId(user.getBudget().getId()));
        return "test_incomeCategories";
    }

    @GetMapping(value = "/add")
    public String showCreateIncomeCategoryForm(Model model) {
        model.addAttribute("action", "/home/incomeCategory/add");
        return "test_incomeCategoryForm";
    }

    @PostMapping(value = "/add")
    public String createIncomeCategory(@AuthenticationPrincipal User user, @ModelAttribute IncomeCategory incomeCategory) {
        incomeCategory.setBudget(user.getBudget());
        incomeCategoryService.create(incomeCategory);
        return "redirect:http://localhost:8080/home/incomeCategory/all";
    }

    @GetMapping(value = "/edit/{id}")
    public String showEditIncomeCategoryForm(@PathVariable Long id, Model model) {
        model.addAttribute("action", "/home/incomeCategory/edit/" + id);
        model.addAttribute("incomeCategory", incomeCategoryService.findById(id));
        return "test_incomeCategoryForm";
    }

    @PostMapping(value = "/edit/{id}")
    public String editIncomeCategory(@PathVariable Long id, @ModelAttribute IncomeCategory incomeCategory) {
        incomeCategoryService.edit(incomeCategory);
        return "redirect:http://localhost:8080/home/incomeCategory/all";
    }

    @GetMapping(value = "delete/{id}")
    public String deleteIncomeCategory(@PathVariable Long id) {
        incomeCategoryService.deleteById(id);
        return "redirect:http://localhost:8080/home/incomeCategory/all";
    }
}
