package cam.imitation.storage;

import cam.imitation.db.DbController;
import cam.imitation.db.model.Snapshot;
import cam.imitation.messager.MessageManager;
import cam.imitation.storage.exception.StorageFileNotFoundException;
import cam.imitation.storage.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

@Controller
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH:MM:SS");

    private DbController dbController;
    private MessageManager messageManager;
    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService, DbController dbController, MessageManager messageManager) {
        this.storageService = storageService;
        this.dbController = dbController;
        this.messageManager = messageManager;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
            path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                    "serveFile", path.getFileName().toString()).build().toString())
            .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"").body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("location") String location,
                                   @RequestParam("sendDelay")Long sendDelay,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("messager",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        dbController.saveSnapshot(new Snapshot(file.getOriginalFilename(), dateFormat.format(new Date()), location, sendDelay));

        return "redirect:/";
    }

    @GetMapping("/startSending")
    public String startSending(){
        messageManager.initSending();
        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
