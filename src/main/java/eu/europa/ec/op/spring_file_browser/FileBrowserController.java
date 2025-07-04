package eu.europa.ec.op.spring_file_browser;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
public class FileBrowserController {
    private final FileService fileService;
    private final String baseDir;

    @Autowired
    public FileBrowserController(FileService fileService) {
        this.fileService = fileService;
        // Use FOLDER_PATH env variable if present, else default to "."
        this.baseDir = Optional.ofNullable(System.getenv("FOLDER_PATH")).orElse(".");
    }

    @GetMapping("/")
    public String listFiles(@RequestParam(value = "dir", required = false) String dir, Model model, HttpServletRequest request) {
        String effectiveDir = (dir == null || dir.isEmpty()) ? baseDir : dir;
        String userDir = System.getProperty("user.dir");
        Boolean exists = Files.isDirectory(Paths.get(userDir, effectiveDir));
        List<String> files = fileService.listFiles(effectiveDir);
        model.addAttribute("files", files);
        model.addAttribute("userDir", userDir);
        model.addAttribute("currentDir", effectiveDir);
        model.addAttribute("exists", exists);
        String host = request.getServerName();
        model.addAttribute("hostName", host);
        return "browser";
    }

    @PostMapping("/create")
    public String createFile(@RequestParam("dir") String dir, @RequestParam("filename") String filename, Model model) {
        String effectiveDir = (dir == null || dir.isEmpty()) ? baseDir : dir;
        boolean success = fileService.createFile(effectiveDir, filename);
        model.addAttribute("created", success);
        return "redirect:/?dir=" + effectiveDir;
    }
}
