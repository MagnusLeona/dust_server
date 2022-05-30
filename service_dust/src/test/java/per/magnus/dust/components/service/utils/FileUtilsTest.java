package per.magnus.dust.components.service.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

class FileUtilsTest {

    @Test
    void readFile() throws URISyntaxException, IOException {

        String bytes = FileUtils.readFileAsString("C:\\Magnus\\articles\\1.md");
    }
}