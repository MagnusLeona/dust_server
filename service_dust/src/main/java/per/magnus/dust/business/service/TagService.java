package per.magnus.dust.business.service;

import org.springframework.stereotype.Service;
import per.magnus.dust.business.domain.Tag;
import per.magnus.dust.business.mapper.TagMapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagService {

    @Resource
    TagMapper tagMapper;

    public List<Tag> queryAllTags() {
        return tagMapper.queryTags();
    }

    public List<Tag> queryAllTagsByType(Integer type) {
        return tagMapper.queryTagsByType(type);
    }
}
