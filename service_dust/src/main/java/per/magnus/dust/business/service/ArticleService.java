package per.magnus.dust.business.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import per.magnus.dust.business.domain.Article;
import per.magnus.dust.business.domain.Tag;
import per.magnus.dust.business.domain.User;
import per.magnus.dust.business.domain.UserArticle;
import per.magnus.dust.business.mapper.ArticleMapper;
import per.magnus.dust.components.service.utils.FileUtils;
import per.magnus.dust.components.web.exception.ext.UserArticleException;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class ArticleService {

    @Resource
    ArticleMapper articleMapper;

    @Resource
    @Qualifier("batch")
    SqlSession batchSqlSession;

    @Resource
    ArticleService articleService;

    @Resource
    UserArticleService userArticleService;

    @Resource
    UserService userService;

    public Article getArticleById(Long id) {
        //返回article
        return articleMapper.getArticleById(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void insertArticle(Article article) throws UserArticleException, IOException {
        // 新增任务
        if (Objects.isNull(article)) {
            throw UserArticleException.nullParameterException();
        }
        // 保存文章信息，同时还需要写入文章内容到本地
        articleMapper.insertArticle(article);
        // 批量写入文章的tag
        // 在同一个事务中，不允许同时出现两个类型的ExecutorType
        // 所以批量插入tag只能另开事务执行
        articleService.insertArticleTag(article);
        FileUtils.saveFile(article.getSavedFileName(), article.getContent());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void insertArticleTag(Article article) {
        // 执行article插入tag的逻辑
        List<Tag> tagList = article.getTagList();
        if (!Objects.isNull(tagList) && !tagList.isEmpty()) {
            ArticleMapper mapper = this.batchSqlSession.getMapper(ArticleMapper.class);
            tagList.forEach(item -> mapper.saveArticleTags(article, item));
        }
    }

    public UserArticle getArticleDetail(Long id, User user) throws IOException, UserArticleException {
        Article articleById = getArticleById(id);
        if (Objects.isNull(articleById)) {
            throw UserArticleException.nullParameterException();
        }
        articleById.setContent(FileUtils.readFileAsString("C:\\Magnus\\articles\\" + articleById.getSavedFileName()));
        UserArticle userArticle = userArticleService.queryUserArticle(user, articleById);
        userArticle.setArticle(articleById);
        userArticle.setUser(user);
        // 新增功能--根据是否登录，判断是否已经点赞/收藏
        return userArticle;
    }

    public List<Article> getArticlesByType(Integer id) {
        // 根据类型筛选数据
        return articleMapper.getArticleByType(id);
    }
}
