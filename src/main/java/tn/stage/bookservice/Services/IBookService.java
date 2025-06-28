package tn.stage.bookservice.Services;
import tn.stage.bookservice.Entities.Book;

import java.util.Date;
import java.util.List;

public interface IBookService {

    public Book addBook(Book book, List<String> categories);
    public Book updateBook(Book book, List<String> categories);
    public void deleteBook(Long id);
    public List<Book> retreiveAllBooks();
    public Book retreiveBook(long id);

    public Book addToLibrary(long id);
    public Book addToLFavorites(long id);

    public Book updateBookStart(Book book, Date start);
    public Book updateBookEnd(Book book, Date end);
    public Book updateBookProgress(Book book, float progress);
    public Book updateBookRating(Book book, int rating);

}
