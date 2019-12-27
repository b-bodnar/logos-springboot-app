package ua.bohdan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BaseController {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String sayHello() {
        return "Hello World";
    }

    @GetMapping("/test") // path =
    public String test() {
        return "Test endpoint";
    }

    @GetMapping("/category")
    public List<String> showCategories() {
        return getCategories();
    }

    @GetMapping("/category/{categoryIndex}")
    public String showCategoryByIndex(
            @PathVariable("categoryIndex") int index
    ) {
        if (index < 0 || index > getCategories().size() - 1) {
            return "Incorrect index";
        }

        return getCategories().get(index);
    }

    @PutMapping("/category/{categoryIndex}")
    public ResponseEntity<?> updateCategory(
            @PathVariable("categoryIndex") int index
    ) {
        List<String> categories = getCategories();

        if (index < 0 || index > getCategories().size() - 1) {
            return new ResponseEntity<>("Incorrect index",
                    HttpStatus.BAD_REQUEST);
        }
        categories.set(index, "Logos Java Course");
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    private List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        categories.add("Java");
        categories.add("JPA/Hibernate");
        categories.add("HTML/CSS");
        categories.add("C#");
        categories.add("JavaScript");
        categories.add("Flutter");
        return categories;
    }

}
