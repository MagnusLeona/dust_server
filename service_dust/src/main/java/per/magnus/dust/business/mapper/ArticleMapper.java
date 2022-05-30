package per.magnus.dust.business.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import per.magnus.dust.business.domain.Article;
import per.magnus.dust.business.domain.Tag;

import java.util.List;

@Mapper
public interface ArticleMapper {

    Article getArticleById(Long id);

    void insertArticle(@Param("article") Article article);

    void saveArticleTags(@Param("article") Article article, @Param("tag") Tag tag);

    List<Article> getArticleByType(Integer id);

    Long queryFans(@Param("article") Article article);
}
