package per.magnus.dust.business.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

@Repository
@Slf4j
public class FileRepository {

    @Value("${dust.upload.file.path}")
    String articleDirBase;

    public byte[] downloadFile(String filePath) {
        //
        try {
            File file = new File(articleDirBase + filePath);
            log.info("download file: " + filePath);

            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            byte[] bytes = new byte[bufferedInputStream.available()];
            bufferedInputStream.read(bytes);
            bufferedInputStream.close();
            return bytes;
        } catch (Exception e) {
            return null;
        }
    }
}
