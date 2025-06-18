package tn.stage.bookservice.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.stage.bookservice.Entities.Category;
import tn.stage.bookservice.Services.CategoryService;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/addCategory")
    Category addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @PutMapping("/updateCategory")
    Category updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    @GetMapping("/retreiveAllCategories")
    List<Category> retreiveAllCategories() {
        return categoryService.retreiveAllCategories();
    }

    @GetMapping("/retreiveCategory{id}")
    Category retreiveCategory(@PathVariable long id) {
        return categoryService.retreiveCategory(id);
    }

    @DeleteMapping("/deleteCategory/{id}")
    void deleteCategory(@PathVariable long id){
        categoryService.deleteCategory(id);
    }
}
