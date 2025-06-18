package tn.stage.bookservice.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.stage.bookservice.Entities.Book;
import tn.stage.bookservice.Services.BookService;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/addBook")
    Book addBook(@RequestBody Book book, @RequestParam List<String> categories) {
        return bookService.addBook(book,categories);
    }

    @PutMapping("/updateBook")
    Book updateBook(@RequestBody Book book, @RequestParam List<String> categories) {
        return bookService.updateBook(book,categories);
    }

    @GetMapping("/retreiveAllBooks")
    List<Book> retreiveAllBooks() {
        return bookService.retreiveAllBooks();
    }

    @GetMapping("/retreiveBook/{id}")
    Book retreiveBook(@PathVariable long id) {
        return bookService.retreiveBook(id);
    }

    @DeleteMapping("/deleteBook/{id}")
    void deleteBook(@PathVariable long id){
        bookService.deleteBook(id);
    }

    @GetMapping("/reviews/{bookId}")
    public List<Object> getReviewsForBook(@PathVariable Long bookId) {
        return bookService.getReviewsByBookId(bookId);
    }

}
