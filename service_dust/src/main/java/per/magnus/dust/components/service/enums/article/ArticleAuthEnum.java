package per.magnus.dust.components.service.enums.article;

public enum ArticleAuthEnum {
    ARTICLE_AUTH_CREATOR(0),
    ARTICLE_AUTH_UPDATER(1),
    ARTICLE_AUTH_COLLECTOR(2),
    ARTICLE_AUTH_NULL(3);

    private final int auth;

    ArticleAuthEnum(int auth) {
        this.auth = auth;
    }

    public int getAuth() {
        return this.auth;
    }
}
