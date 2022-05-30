package per.magnus.dust.business.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Param;
import per.magnus.dust.components.service.enums.article.ArticleAuthEnum;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserArticle {
    public Long id;
    public User user;
    public Article article;
    public Integer role;
    public Boolean marked;
    public Boolean fan;

    public UserArticle(User user, Article article) {
        this.user = user;
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public Article getArticle() {
        return article;
    }

    public Boolean getFan() {
        if (!Objects.isNull(fan)) {
            return fan;
        }
        return false;
    }

    public Boolean getMarked() {
        if (!Objects.isNull(marked)) {
            return marked;
        }
        return false;
    }

    public static UserArticle newUserFancyingArticle(User user, Article article) {
        return new UserArticleBuilder().setArticle(article).setUser(user).setFan(true).setMarked(false).setRole(ArticleAuthEnum.ARTICLE_AUTH_NULL).build();
    }

    public static UserArticle newUserMarkingArticle(User user, Article article) {
        return new UserArticleBuilder().setArticle(article).setUser(user).setFan(false).setMarked(true).setRole(ArticleAuthEnum.ARTICLE_AUTH_NULL).build();
    }

    public static class UserArticleBuilder {
        UserArticle userArticle = new UserArticle();

        public UserArticleBuilder setUser(User user) {
            userArticle.setUser(user);
            return this;
        }

        public UserArticleBuilder setArticle(Article article) {
            userArticle.setArticle(article);
            return this;
        }

        public UserArticleBuilder setMarked(Boolean mark) {
            userArticle.setMarked(mark);
            return this;
        }

        public UserArticleBuilder setFan(Boolean fan) {
            userArticle.setFan(fan);
            return this;
        }

        public UserArticleBuilder setRole(ArticleAuthEnum articleAuthEnum) {
            userArticle.setRole(articleAuthEnum.getAuth());
            return this;
        }

        public UserArticle build() {
            return userArticle;
        }
    }
}
