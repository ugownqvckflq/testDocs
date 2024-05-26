package nazarov.example.demo.service;

import nazarov.example.demo.model.Document;
import nazarov.example.demo.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Optional<Document> findById(Long id){
        return documentRepository.findById(id);
    }

    public List<Document> findAll(){
        return documentRepository.findAll();
    }

    public Document saveDocument(Document document) {
        // Проверяем, существует ли документ с такими же title, type и date
        Optional<Document> existingDocument = documentRepository.findByTitleAndTypeAndDate(
                document.getTitle(),
                document.getType(),
                document.getDate()
        );

        // Если документ существует, выбрасываем исключение или обрабатываем как нужно
        if (existingDocument.isPresent()) {
            throw new IllegalArgumentException("Document with the same title, type, and date already exists.");
        }

        // Сохраняем новый документ
        return documentRepository.save(document);
    }

    public List<Document> findByTitle(String title) {
        return documentRepository.findByTitleContaining(title);
    }

    public List<Document> findByType(String type) {
        return documentRepository.findByTypeContaining(type);
    }

    public List<Document> findByDate(Date date) {
        return documentRepository.findByDate(date);
    }

    public List<Document> findByTypeAndDate(String type, Date date) {
        return documentRepository.findByTypeAndDate(type, date);
    }

    public List<Document> findByTitleAndDate(String title, Date date) {
        return documentRepository.findByTitleAndDate(title, date);
    }

    public List<Document> findByTitleAndType(String title, String type) {
        return documentRepository.findByTitleAndType(title, type);
    }

    public List<Document> searchDocuments(String title, String type, Date date) {
        return documentRepository.findByTitleContainingAndTypeContainingAndDate(title, type, date);
    }

    public Document save(Document document){
        List<Document> documents = documentRepository.findAll();

        return documentRepository.save(document);
    }

    public void deleteById(Long id){
        documentRepository.deleteById(id);
    }




}
