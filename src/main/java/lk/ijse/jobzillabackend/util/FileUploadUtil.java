package lk.ijse.jobzillabackend.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    public static  void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {

        Path uploadPath = Paths.get("C:\\Users\\kesha\\Documents\\IJSE GDES 69\\Advanced-Api-Development\\final-project\\jobZilla-backend\\src\\main\\resources\\uploads\\"+uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try(InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ignored) {

        }

    }

}
