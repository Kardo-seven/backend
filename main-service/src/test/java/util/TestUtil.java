package util;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtil {

    public static MultipartFile createMultipartFile(String filePath) throws IOException {
        MultipartFile file = null;
        InputStream in = null;

        try {
            Path newFilePath = Paths.get(filePath);
            File template = new File(newFilePath.toUri());
            File content = File.createTempFile(newFilePath.getFileName().toString(), "", template.getParentFile());
            content.deleteOnExit();
            in = new FileInputStream(content);
            file = new MockMultipartFile(filePath, content.getName(), content.getName(), in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }

        return file;
    }
}
