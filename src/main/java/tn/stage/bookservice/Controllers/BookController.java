package tn.stage.bookservice.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.stage.bookservice.Entities.Book;
import tn.stage.bookservice.Services.BookService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    private final String UPLOAD_DIR = "uploads/";

    // === CRUD BASIQUE DES LIVRES ===

    @PostMapping("/addBook")
    Book addBook(@RequestBody Book book, @RequestParam List<String> categories) {
        return bookService.addBook(book, categories);
    }

    @PutMapping("/updateBook")
    Book updateBook(@RequestBody Book book, @RequestParam List<String> categories) {
        return bookService.updateBook(book, categories);
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
    void deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
    }

    // === FONCTIONNALITÉS SPÉCIFIQUES ===

    @GetMapping("/reviews/{bookId}")
    public List<Object> getReviewsForBook(@PathVariable Long bookId) {
        return bookService.getReviewsByBookId(bookId);
    }

    @PutMapping("/addToLibrary/{bookId}")
    Book addToLibrary(@PathVariable Long bookId) {
        return bookService.addToLibrary(bookId);
    }

    @PutMapping("/addToFavorites/{bookId}")
    Book addToFavorites(@PathVariable Long bookId) {
        return bookService.addToLFavorites(bookId);
    }

    // === GESTION DES IMAGES ===

    @PostMapping("/addBookWithImage")
    public ResponseEntity<?> addBookWithImage(
            @RequestParam("book") String bookJson,
            @RequestParam("categories") List<String> categories,
            @RequestParam(value = "coverImage", required = false) MultipartFile coverImage) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Book book = objectMapper.readValue(bookJson, Book.class);

            if (coverImage != null && !coverImage.isEmpty()) {
                String fileName = saveImageWithUniqueId(coverImage);
                book.setCover(fileName);
            }

            Book savedBook = bookService.addBook(book, categories);
            return ResponseEntity.ok(savedBook);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'ajout du livre: " + e.getMessage());
        }
    }

    // Endpoint pour servir les images
    @GetMapping("/uploads/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Path file = Paths.get(UPLOAD_DIR).resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, getContentType(filename))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // === MÉTHODES UTILITAIRES PRIVÉES ===

    private String saveImageWithUniqueId(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = "";
        if (originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String fileName = UUID.randomUUID().toString() + extension;
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    private String getContentType(String filename) {
        String extension = filename.substring(filename.lastIndexOf(".")).toLowerCase();
        switch (extension) {
            case ".jpg":
            case ".jpeg":
                return "image/jpeg";
            case ".png":
                return "image/png";
            case ".gif":
                return "image/gif";
            case ".webp":
                return "image/webp";
            case ".bmp":
                return "image/bmp";
            default:
                return "application/octet-stream";
        }
    }
}