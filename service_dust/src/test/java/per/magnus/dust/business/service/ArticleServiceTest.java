package per.magnus.dust.business.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import per.magnus.dust.business.domain.Article;
import per.magnus.dust.business.domain.Tag;
import per.magnus.dust.business.domain.User;
import per.magnus.dust.business.domain.UserArticle;
import per.magnus.dust.business.mapper.ArticleMapper;
import per.magnus.dust.components.web.exception.ext.UserArticleException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceTest {

    Article article;
    String name = "this is article";

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleMapper articleMapper;

    @BeforeEach
    void setup() {
        article = new Article();
        article.setName(name);
        article.setDescription("this is description");
        article.setFileName("Java多线程核心技术");
        article.setContent("### Java多线程核心技术标题");
    }

    @Test
    void getArticleById() {
        Article article = Article.createArticle(1);
        Article articleById = articleService.getArticleById(1L);
        assert articleById == null;
    }


    @Test
    void insertArticle() throws UserArticleException, IOException {

        Tag tag = new Tag();
        tag.setId(1L);
        Tag tag1 = new Tag();
        tag1.setId(1L);

        List<Tag> list = new ArrayList<>();
        list.add(tag);
        list.add(tag1);

        this.article.setTagList(list);

        articleService.insertArticle(this.article);

        assert article.getName().equals(name);
        Long id = article.getId();
        assert id != null;
    }

    @Test
    void insertArticleTag() throws UserArticleException, IOException {
        articleService.insertArticle(this.article);
        // 保存article和tag的信息
        Tag tag = new Tag();
        tag.setType(1);
        tag.setName("前端");
        tag.setId(1L);
        this.articleMapper.saveArticleTags(this.article, tag);
    }

    @Test
    void getArticleByType() {
        List<Article> articleByType = this.articleMapper.getArticleByType(1);
        System.out.println(articleByType);
    }

    @Test
    void getArticleDetail() throws UserArticleException, IOException {
        User user = new User();
        user.setId(1L);
        UserArticle articleDetail = articleService.getArticleDetail(1L, user);
        System.out.println(articleDetail);
    }
}