package per.magnus.dust.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    public Long id;
    public String name;
    public String description;
    public String fileName;
    public String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public LocalDateTime updateTime;
    public List<Tag> tagList;
    public User author;

    // 从MAP中创建article
    public static Article createArticleFromMap(Map<String, Object> map) {
        Article article = new Article();
        if (map.containsKey("id")) {
            article.setId((Long) map.get("id"));
        }
        if (map.containsKey("name")) {
            article.setName((String) map.get("name"));
        }
        if (map.containsKey("description")) {
            article.setDescription((String) map.get("description"));
        }
        if (map.containsKey("fileName")) {
            article.setFileName((String) map.get("fileName"));
        }
        if (map.containsKey("content")) {
            article.setContent((String) map.get("content"));
        }
        // 需要设置修改的时间，如果没有上送则默认为当前交易的时间。
        if (map.containsKey("updateTime")) {
            // 拿到的是秒
            String updateTime = (String) map.get("updateTime");
            try {
                article.setUpdateTime(LocalDateTime.parse(updateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } catch (Exception e) {
                article.setUpdateTime(LocalDateTime.now());
            }
        } else {
            article.setUpdateTime(LocalDateTime.now());
        }
        if (map.containsKey("tags")) {
            List<Map<String, Object>> tags = (List) map.get("tags");
            if (!Objects.isNull(tags) && !tags.isEmpty()) {
                List<Tag> collect = tags.stream().map(Tag::createTagFromMap).collect(Collectors.toList());
                if (!collect.isEmpty()) {
                    article.setTagList(collect);
                }
            }
        }
        return article;
    }

    // 创建实体
    public static Article createArticle(long id) {
        Article article = new Article();
        article.setId(id);
        return article;
    }

    public String getSavedFileName() {
        return this.id + "_" + this.fileName;
    }
}
