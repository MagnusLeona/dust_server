package per.magnus.dust.business.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import per.magnus.dust.business.domain.Tag;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TagServiceTest {

    @Resource
    TagService tagService;

    @Test
    void queryAllTags() {
        List<Tag> tags = tagService.queryAllTags();
        System.out.println(tags);
    }

    @Test
    void queryAllTagsByType() {
        List<Tag> tags = tagService.queryAllTagsByType(1);
        System.out.println(tags);
    }
}