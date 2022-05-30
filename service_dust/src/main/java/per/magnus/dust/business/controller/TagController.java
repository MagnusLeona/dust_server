package per.magnus.dust.business.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.magnus.dust.business.domain.Tag;
import per.magnus.dust.business.service.TagService;
import per.magnus.dust.components.web.entity.DustResponse;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/tag")
@SuppressWarnings("unused")
public class TagController {

    @Resource
    TagService tagService;

    @RequestMapping("/query/all")
    public DustResponse getAllTags() {
        List<Tag> tags = tagService.queryAllTags();
        return DustResponse.okResponse(tags);
    }
}
