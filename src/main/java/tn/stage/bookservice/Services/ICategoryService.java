package tn.stage.bookservice.Services;
import tn.stage.bookservice.Entities.Category;
import java.util.List;

public interface ICategoryService {

    public Category addCategory(Category category);
    public Category updateCategory(Category category);
    public void deleteCategory(Long id);
    public List<Category> retreiveAllCategories();
    public Category retreiveCategory(long id);

}
