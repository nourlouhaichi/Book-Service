package tn.stage.bookservice.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.stage.bookservice.Entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
