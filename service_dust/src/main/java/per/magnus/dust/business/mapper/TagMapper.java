package per.magnus.dust.business.mapper;

import org.apache.ibatis.annotations.Mapper;
import per.magnus.dust.business.domain.Tag;

import java.util.List;

@Mapper
@SuppressWarnings("unused")
public interface TagMapper {

    public List<Tag> queryTags();

    public List<Tag> queryTagsByType(Integer type);

}
