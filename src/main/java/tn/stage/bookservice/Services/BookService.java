package tn.stage.bookservice.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.stage.bookservice.Entities.Book;
import tn.stage.bookservice.Repositories.BookRepository;
import tn.stage.bookservice.Repositories.CategoryRepository;
import java.util.List;

@Service
public class BookService implements IBookService{
    @Autowired
    BookRepository bookRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Book addBook(Book book, List<String> categories) {
        book.setCategories(categoryRepository.findAllByNameIn(categories));
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book, List<String> categories) {
        book.setCategories(categoryRepository.findAllByNameIn(categories));
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).get();
        book.getCategories().clear();
        bookRepository.save(book);
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> retreiveAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book retreiveBook(long id) {
        return bookRepository.findById(id).get();
    }
}
