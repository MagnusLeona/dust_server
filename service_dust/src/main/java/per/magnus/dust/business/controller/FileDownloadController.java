package per.magnus.dust.business.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import per.magnus.dust.business.repository.FileRepository;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class FileDownloadController {

    @Resource
    FileRepository fileRepository;

    @RequestMapping("/download/{name}")
    public void download(@PathVariable("name") String name, HttpServletResponse httpServletResponse) throws IOException {
        byte[] bytes = fileRepository.downloadFile(name);
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        outputStream.write(bytes);
        outputStream.close();
    }
}
