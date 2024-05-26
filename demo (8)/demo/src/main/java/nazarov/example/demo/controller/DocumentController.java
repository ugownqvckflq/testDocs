package nazarov.example.demo.controller;

import nazarov.example.demo.model.Document;
import nazarov.example.demo.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class DocumentController {


    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/documents")
    public String findByParam(@RequestParam(required = false) String title,
                              @RequestParam(required = false) String type,
                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                              Model model) {
        List<Document> documents;

        // Проверяем каждый параметр поиска
        if (title != null && type == null && date == null) {
            documents = documentService.findByTitle(title);
        } else if (title == null && type != null && date == null) {
            documents = documentService.findByType(type);
        } else if (title == null && type == null && date != null) {
            documents = documentService.findByDate(date);
        } else if (title != null && type != null && date == null) {
            documents = documentService.findByTitleAndType(title, type);
        } else if (title != null && type == null && date != null) {
            documents = documentService.findByTitleAndDate(title, date);
        } else if (title == null && type != null && date != null) {
            documents = documentService.findByTypeAndDate(type, date);
        } else if (title != null && type != null && date != null) {
            documents = documentService.searchDocuments(title, type, date);
        } else {
            // Если ни один параметр не указан, возвращаем все документы
            documents = documentService.findAll();
        }

        model.addAttribute("documents", documents);
        return "all-doc";
    }



    @GetMapping("/document-create")
    public String createDocument(Document document){
        return "doc-create";
    }

    @PostMapping("/document-create")
    public String createDoc(Document document){
       documentService.saveDocument(document);
        return "redirect:/documents";

    }

    @GetMapping("document-delete/{id}")
    public String delete(@PathVariable("id") Long id){
        documentService.deleteById(id);
        return "redirect:/documents";
    }

    @GetMapping("document-update/{id}")
    public String updateDocument(@PathVariable("id") Long id, Model model){
        Optional<Document> document = documentService.findById(id);
        model.addAttribute("document", document);
        return "/doc-update";
    }


    @PostMapping("/document-update")
    public String updateDoc(Document document){
        documentService.save(document);
        return "redirect:/documents";
    }


}
