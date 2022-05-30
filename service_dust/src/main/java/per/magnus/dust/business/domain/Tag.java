package per.magnus.dust.business.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    Long id;
    String name;
    Integer type;

    public static Tag createTagFromMap(Map<String, Object> map) {
        Tag tag = new Tag();
        if (map.containsKey("id")) {
            tag.setId(Long.parseLong(map.get("id").toString()));
        }
        if (map.containsKey("name")) {
            tag.setName((String) map.get("name"));
        }
        if (map.containsKey("type")) {
            tag.setType((Integer) map.get("type"));
        }
        return tag;
    }
}
