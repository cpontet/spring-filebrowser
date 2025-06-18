package eu.europa.ec.op.spring_file_browser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        // Use FILES_PATH env variable if present, else default to "."
        this.baseDir = Optional.ofNullable(System.getenv("FILES_PATH")).orElse(".");
    }

    @GetMapping("/")
    public String listFiles(@RequestParam(value = "dir", required = false) String dir, Model model) {
        String effectiveDir = (dir == null || dir.isEmpty()) ? baseDir : dir;
        List<String> files = fileService.listFiles(effectiveDir);
        model.addAttribute("files", files);
        model.addAttribute("currentDir", effectiveDir);
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
