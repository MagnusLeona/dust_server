package per.magnus.dust.business.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import per.magnus.dust.business.domain.Article;
import per.magnus.dust.business.domain.User;
import per.magnus.dust.business.domain.UserArticle;
import per.magnus.dust.business.mapper.UserArticleMapper;
import per.magnus.dust.components.service.enums.article.ArticleAuthEnum;
import per.magnus.dust.components.web.exception.DustException;
import per.magnus.dust.components.web.exception.ext.UserArticleException;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class UserArticleService {

    @Resource
    UserArticleMapper userArticleMapper;

    @Resource
    ArticleService articleService;

    @Transactional(rollbackFor = Exception.class)
    public void createArticleForUser(UserArticle userArticle) throws DustException, IOException {
        // 保存user - article 的关系列表
        if (Objects.isNull(userArticle) || Objects.isNull(userArticle.getUser()) || Objects.isNull(userArticle.getArticle())) {
            throw UserArticleException.nullParameterException();
        }
        // 保存文章
        articleService.insertArticle(userArticle.getArticle());
        userArticleMapper.insertUserArticle(userArticle);
    }

    public List<Article> queryArticlesForUser(User user) {
        return userArticleMapper.queryArticlesForUser(user);
    }

    public void likeArticle(User user, Article article) {
        // 先查询是否有user-article的关系
        UserArticle userArticle = userArticleMapper.selectUserArticle(user, article);
        if (!Objects.isNull(userArticle)) {
            // 如果已经有这个关系表了，那么需要修改这个关系
            userArticle.setFan(true);
            // 保存
            userArticleMapper.updateUserArticleFans(userArticle);
        } else {
            UserArticle newUserFancyingArticle = UserArticle.newUserFancyingArticle(user, article);
            // 保存这个关系
            userArticleMapper.insertUserArticle(newUserFancyingArticle);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void cancelLikeArticle(User user, Article article) {
        UserArticle userArticle = userArticleMapper.selectUserArticle(user, article);
        if (!Objects.isNull(userArticle)) {
            if (userArticle.getMarked() || userArticle.getRole().compareTo(ArticleAuthEnum.ARTICLE_AUTH_NULL.getAuth()) != 0) {
                userArticle.setFan(false);
                userArticleMapper.updateUserArticleFans(userArticle);
            } else {
                // 删除此注释
                userArticleMapper.deleteUserArticle(userArticle);
            }
        }
    }

    public void markArticle(User user, Article article) {
        UserArticle userArticle = userArticleMapper.selectUserArticle(user, article);
        if (!Objects.isNull(userArticle)) {
            if (!userArticle.getMarked()) {
                userArticle.setMarked(true);
                userArticleMapper.updateUserArticleMark(userArticle);
            }
        } else {
            UserArticle newUserMarkingArticle = UserArticle.newUserMarkingArticle(user, article);
            userArticleMapper.insertUserArticle(newUserMarkingArticle);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void cancelMarkArticle(User user, Article article) {
        UserArticle userArticle = userArticleMapper.selectUserArticle(user, article);
        if (!Objects.isNull(userArticle)) {
            if (userArticle.getFan() || userArticle.getRole().compareTo(ArticleAuthEnum.ARTICLE_AUTH_NULL.getAuth()) != 0) {
                userArticle.setMarked(false);
                userArticleMapper.updateUserArticleMark(userArticle);
            } else {
                // 删除此注释
                userArticleMapper.deleteUserArticle(userArticle);
            }
        }
    }

    public UserArticle queryUserArticle(User user, Article article) {
        if (Objects.isNull(user) || Objects.isNull(article)) {
            return new UserArticle.UserArticleBuilder().setUser(user).setArticle(article).setRole(ArticleAuthEnum.ARTICLE_AUTH_NULL).setMarked(false).setFan(false).build();
        }
        return userArticleMapper.selectUserArticle(user, article);
    }

    public Boolean queryIfFanned(User user, Article article) {
        if (Objects.isNull(user) || Objects.isNull(article)) {
            return false;
        }
        UserArticle userArticle = userArticleMapper.queryIfFanned(user, article);
        return !Objects.isNull(userArticle) && userArticle.getFan();
    }

    public List<Article> queryMarkedArticles(User user) {
        return userArticleMapper.queryMarkedArticles(user);
    }
}
