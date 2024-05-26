package nazarov.example.demo.repository;

import nazarov.example.demo.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByTitleAndTypeAndDate(String title, String type, Date date);
    List<Document> findByTitleContainingAndTypeContainingAndDate(String title, String type, Date date); // Add this method



    List<Document> findByTypeAndDate(String type, Date date);
    List<Document> findByTitleAndDate(String title, Date date);
    List<Document> findByTitleAndType(String title, String type);

    List<Document> findByTitleContaining(String title);
    List<Document> findByTypeContaining(String type);
    List<Document> findByDate(Date date);
}
