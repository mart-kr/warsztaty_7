package pl.coderslab.warsztaty_7.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @GetMapping("/home")
    public String hello() {
        return "Hello world";
    }
}
