package tn.stage.bookservice.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.stage.bookservice.Entities.Book;
import tn.stage.bookservice.Entities.Category;
import tn.stage.bookservice.Repositories.BookRepository;
import tn.stage.bookservice.Repositories.CategoryRepository;
import java.util.List;

@Service
public class CategoryService implements ICategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BookRepository bookRepository;

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).get();
        for (Book book : category.getBooks()) {
            book.getCategories().remove(category);
            bookRepository.save(book);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> retreiveAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category retreiveCategory(long id) {
        return categoryRepository.findById(id).get();
    }
}
