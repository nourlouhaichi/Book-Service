package tn.stage.bookservice.Entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idBook;
    String title;
    @Column(columnDefinition = "TEXT")
    String summary;
    String cover;
    String author;
    String series;
    boolean status;
    boolean liked;
    float progress;
    int pages;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date start;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date end;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date publicationInfo;


    @ManyToMany(cascade = CascadeType.ALL)
    List<Category> categories;

}
