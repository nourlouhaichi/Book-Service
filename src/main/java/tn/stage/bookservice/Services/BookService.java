package tn.stage.bookservice.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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

    @Override
    public Book addToLibrary(long id) {
        Book book = bookRepository.findById(id).get();
        if (!book.isStatus())
        book.setStatus(true);
        else book.setStatus(false);
        return bookRepository.save(book);
    }

    @Override
    public Book addToLFavorites(long id) {
        Book book = bookRepository.findById(id).get();
        if (!book.isLiked())
            book.setLiked(true);
        else book.setLiked(false);
        return bookRepository.save(book);
    }

    @Autowired
    private RestTemplate restTemplate;

    private final String REVIEW_SERVICE_URL = "http://localhost:8090/review-service";

    //exemple restTemlate
    public List<Object> getReviewsByBookId(Long bookId) {
        String url = REVIEW_SERVICE_URL + "/review/retrieveByBook/" + bookId;
        return restTemplate.getForObject(url, List.class);
    }

}
