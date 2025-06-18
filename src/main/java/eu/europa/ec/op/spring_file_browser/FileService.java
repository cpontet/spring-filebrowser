package eu.europa.ec.op.spring_file_browser;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    public List<String> listFiles(String dir) {
        List<String> fileList = new ArrayList<>();
        try {
            Files.list(Paths.get(dir)).forEach(path -> fileList.add(path.getFileName().toString()));
        } catch (IOException e) {
            fileList.add("Error: " + e.getMessage());
        }
        return fileList;
    }

    public boolean createFile(String dir, String filename) {
        Path filePath = Paths.get(dir, filename);
        try {
            Files.createFile(filePath);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
