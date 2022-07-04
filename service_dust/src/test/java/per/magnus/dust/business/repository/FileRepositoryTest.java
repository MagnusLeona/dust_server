package per.magnus.dust.business.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileRepositoryTest {

    @Resource
    FileRepository fileRepository;

    @Test
    void downloadFile() {
        byte[] bytes = fileRepository.downloadFile("D:\\magnus\\articles\\1.pdf");
        assert bytes != null;
        System.out.println(bytes.length);
    }
}