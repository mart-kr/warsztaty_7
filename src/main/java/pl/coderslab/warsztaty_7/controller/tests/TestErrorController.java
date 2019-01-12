package pl.coderslab.warsztaty_7.controller.tests;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestErrorController {

    @RequestMapping("/exception")
    public String notFoundError() {
        throw new NullPointerException("Exception message");
    }
}
