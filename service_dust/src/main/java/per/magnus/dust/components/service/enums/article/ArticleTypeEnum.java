package per.magnus.dust.components.service.enums.article;

public enum ArticleTypeEnum {

    MARKDOWN(1),
    PDF(2);

    final Integer type;

    ArticleTypeEnum(int i) {
        this.type = i;
    }

    public Integer getType() {
        return type;
    }
}
