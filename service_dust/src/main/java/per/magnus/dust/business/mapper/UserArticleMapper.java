package per.magnus.dust.business.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import per.magnus.dust.business.domain.Article;
import per.magnus.dust.business.domain.User;
import per.magnus.dust.business.domain.UserArticle;

import java.util.List;

@Mapper
public interface UserArticleMapper {

    void insertUserArticle(@Param("userArticle") UserArticle userArticle);

    List<Article> queryArticlesForUser(@Param("user") User user);

    UserArticle queryIfFanned(@Param("user") User user, @Param("article") Article article);

    UserArticle selectUserArticle(@Param("user") User user, @Param("article") Article article);

    void updateUserArticleFans(@Param("userArticle") UserArticle userArticle);

    void deleteUserArticle(@Param("userArticle") UserArticle userArticle);

    void updateUserArticleMark(@Param("userArticle") UserArticle userArticle);

    List<Article> queryMarkedArticles(@Param("user") User user);
}
