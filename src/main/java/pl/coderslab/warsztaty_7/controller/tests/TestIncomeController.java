package pl.coderslab.warsztaty_7.controller.tests;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.warsztaty_7.model.IncomeCategory;

@Controller
@RequestMapping("/home")
public class TestIncomeController {

    @GetMapping(path = "/show-incomes")
    public String showIncomes(){
        return "";
    }

    @GetMapping (path = "/add-income-category")
    public String addIncomeCategoryForm(Model model) {
        model.addAttribute("incomeCategory", new IncomeCategory());

        return "test_addIncomeCategory";
    }

    @PostMapping(path = "/add-income-category")
    public String addIncomeCategorySubmit(@ModelAttribute IncomeCategory incomeCategory){
        return "test_addIncomeCategoryResult";
    }

}
