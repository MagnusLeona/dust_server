package per.magnus.dust.business.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import per.magnus.dust.business.domain.Article;
import per.magnus.dust.business.domain.User;
import per.magnus.dust.business.domain.UserArticle;
import per.magnus.dust.components.web.exception.DustException;
import per.magnus.dust.components.web.exception.ext.UserArticleException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserArticleServiceTest {

    @Autowired
    UserArticleService userArticleService;

    UserArticle userArticle;
    User user;
    Article article;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(1L);
        article = new Article();
        article.setName("Vue内容讲解");
        article.setDescription("Vue知识介绍哦~");
        article.setFileName("Vue全解");
        article.setContent("### Vue知识大全");
        userArticle = new UserArticle();
        userArticle.setArticle(article);
        userArticle.setUser(user);
        userArticle.setRole(0);
        userArticle.setMarked(true);
        userArticle.setFan(true);
        article.setUpdateTime(LocalDateTime.now());
    }

    @Test
    void insertUserArticle() throws DustException, IOException {
        userArticleService.createArticleForUser(userArticle);
    }

    @Test
    void testException() {
        userArticle.setArticle(null);
        Assertions.assertThrows(UserArticleException.class, () -> {
            userArticleService.createArticleForUser(userArticle);
        });
    }

    @Test
    void getArticles() {
        List<Article> articles = userArticleService.queryArticlesForUser(user);
        System.out.println(articles);
    }

    @Test
    void likeArticle() {
        article.setId(1L);
        userArticleService.likeArticle(user, article);
    }

    @Test
    void ifFanned() {
        article.setId(1L);
        Boolean aBoolean = userArticleService.queryIfFanned(user, article);
        assert aBoolean;
        article.setId(2L);
        Boolean aBoolean1 = userArticleService.queryIfFanned(user, article);
        assert !aBoolean1;
        user.setId(2L);
        assert !userArticleService.queryIfFanned(user, article);
        article.setId(1L);
        assert !userArticleService.queryIfFanned(user, article);
    }

}