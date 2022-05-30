package per.magnus.dust.business.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ArticleTest {

    @Test
    void createArticleFromMap() {

        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> m1 = new HashMap<>();
        m1.put("id", 1L);
        m1.put("name", "name1");
        m1.put("type", 1);
        list.add(m1);
        Map<String, Object> m2 = new HashMap<>();
        m2.put("id", 2L);
        m2.put("name", "name2");
        list.add(m2);
        Map<String, Object> m3 = new HashMap<>();
        m3.put("id", 3L);
        m3.put("name", "name3");
        list.add(m3);
        map.put("tags", list);

        Article articleFromMap = Article.createArticleFromMap(map);
        List<Tag> tagList = articleFromMap.getTagList();
        assert !tagList.isEmpty();
    }
}