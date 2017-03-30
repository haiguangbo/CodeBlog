package org.codingblog.web.rest;

import org.codingblog.CodeBlogApp;

import org.codingblog.domain.Article;
import org.codingblog.repository.ArticleRepository;
import org.codingblog.service.ArticleService;
import org.codingblog.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static org.codingblog.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ArticleResource REST controller.
 *
 * @see ArticleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CodeBlogApp.class)
public class ArticleResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_LABEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LABEL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_ABSTRACTS = "AAAAAAAAAA";
    private static final String UPDATED_ABSTRACTS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Integer DEFAULT_MONTH = 1;
    private static final Integer UPDATED_MONTH = 2;

    private static final Integer DEFAULT_DAY = 1;
    private static final Integer UPDATED_DAY = 2;

    private static final String DEFAULT_HEAD_IMG_URL = "AAAAAAAAAA";
    private static final String UPDATED_HEAD_IMG_URL = "BBBBBBBBBB";

    private static final String DEFAULT_RENDER_ENGINE = "AAAAAAAAAA";
    private static final String UPDATED_RENDER_ENGINE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ARTICLE_TYPE = 1;
    private static final Integer UPDATED_ARTICLE_TYPE = 2;

    private static final Long DEFAULT_READ_NUM = 1L;
    private static final Long UPDATED_READ_NUM = 2L;

    private static final Long DEFAULT_COMMENT_NUM = 1L;
    private static final Long UPDATED_COMMENT_NUM = 2L;

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restArticleMockMvc;

    private Article article;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ArticleResource articleResource = new ArticleResource(articleService);
        this.restArticleMockMvc = MockMvcBuilders.standaloneSetup(articleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Article createEntity(EntityManager em) {
        Article article = new Article()
            .userId(DEFAULT_USER_ID)
            .labelName(DEFAULT_LABEL_NAME)
            .title(DEFAULT_TITLE)
            .abstracts(DEFAULT_ABSTRACTS)
            .createTime(DEFAULT_CREATE_TIME)
            .year(DEFAULT_YEAR)
            .month(DEFAULT_MONTH)
            .day(DEFAULT_DAY)
            .headImgUrl(DEFAULT_HEAD_IMG_URL)
            .renderEngine(DEFAULT_RENDER_ENGINE)
            .articleType(DEFAULT_ARTICLE_TYPE)
            .readNum(DEFAULT_READ_NUM)
            .commentNum(DEFAULT_COMMENT_NUM)
            .content(DEFAULT_CONTENT);
        return article;
    }

    @Before
    public void initTest() {
        article = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticle() throws Exception {
        int databaseSizeBeforeCreate = articleRepository.findAll().size();

        // Create the Article
        restArticleMockMvc.perform(post("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isCreated());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeCreate + 1);
        Article testArticle = articleList.get(articleList.size() - 1);
        assertThat(testArticle.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testArticle.getLabelName()).isEqualTo(DEFAULT_LABEL_NAME);
        assertThat(testArticle.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testArticle.getAbstracts()).isEqualTo(DEFAULT_ABSTRACTS);
        assertThat(testArticle.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testArticle.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testArticle.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testArticle.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testArticle.getHeadImgUrl()).isEqualTo(DEFAULT_HEAD_IMG_URL);
        assertThat(testArticle.getRenderEngine()).isEqualTo(DEFAULT_RENDER_ENGINE);
        assertThat(testArticle.getArticleType()).isEqualTo(DEFAULT_ARTICLE_TYPE);
        assertThat(testArticle.getReadNum()).isEqualTo(DEFAULT_READ_NUM);
        assertThat(testArticle.getCommentNum()).isEqualTo(DEFAULT_COMMENT_NUM);
        assertThat(testArticle.getContent()).isEqualTo(DEFAULT_CONTENT);
    }

    @Test
    @Transactional
    public void createArticleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articleRepository.findAll().size();

        // Create the Article with an existing ID
        article.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleMockMvc.perform(post("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllArticles() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        // Get all the articleList
        restArticleMockMvc.perform(get("/api/articles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(article.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].labelName").value(hasItem(DEFAULT_LABEL_NAME.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].abstracts").value(hasItem(DEFAULT_ABSTRACTS.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)))
            .andExpect(jsonPath("$.[*].headImgUrl").value(hasItem(DEFAULT_HEAD_IMG_URL.toString())))
            .andExpect(jsonPath("$.[*].renderEngine").value(hasItem(DEFAULT_RENDER_ENGINE.toString())))
            .andExpect(jsonPath("$.[*].articleType").value(hasItem(DEFAULT_ARTICLE_TYPE)))
            .andExpect(jsonPath("$.[*].readNum").value(hasItem(DEFAULT_READ_NUM.intValue())))
            .andExpect(jsonPath("$.[*].commentNum").value(hasItem(DEFAULT_COMMENT_NUM.intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())));
    }

    @Test
    @Transactional
    public void getArticle() throws Exception {
        // Initialize the database
        articleRepository.saveAndFlush(article);

        // Get the article
        restArticleMockMvc.perform(get("/api/articles/{id}", article.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(article.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.labelName").value(DEFAULT_LABEL_NAME.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.abstracts").value(DEFAULT_ABSTRACTS.toString()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY))
            .andExpect(jsonPath("$.headImgUrl").value(DEFAULT_HEAD_IMG_URL.toString()))
            .andExpect(jsonPath("$.renderEngine").value(DEFAULT_RENDER_ENGINE.toString()))
            .andExpect(jsonPath("$.articleType").value(DEFAULT_ARTICLE_TYPE))
            .andExpect(jsonPath("$.readNum").value(DEFAULT_READ_NUM.intValue()))
            .andExpect(jsonPath("$.commentNum").value(DEFAULT_COMMENT_NUM.intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArticle() throws Exception {
        // Get the article
        restArticleMockMvc.perform(get("/api/articles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticle() throws Exception {
        // Initialize the database
        articleService.save(article);

        int databaseSizeBeforeUpdate = articleRepository.findAll().size();

        // Update the article
        Article updatedArticle = articleRepository.findOne(article.getId());
        updatedArticle
            .userId(UPDATED_USER_ID)
            .labelName(UPDATED_LABEL_NAME)
            .title(UPDATED_TITLE)
            .abstracts(UPDATED_ABSTRACTS)
            .createTime(UPDATED_CREATE_TIME)
            .year(UPDATED_YEAR)
            .month(UPDATED_MONTH)
            .day(UPDATED_DAY)
            .headImgUrl(UPDATED_HEAD_IMG_URL)
            .renderEngine(UPDATED_RENDER_ENGINE)
            .articleType(UPDATED_ARTICLE_TYPE)
            .readNum(UPDATED_READ_NUM)
            .commentNum(UPDATED_COMMENT_NUM)
            .content(UPDATED_CONTENT);

        restArticleMockMvc.perform(put("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArticle)))
            .andExpect(status().isOk());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate);
        Article testArticle = articleList.get(articleList.size() - 1);
        assertThat(testArticle.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testArticle.getLabelName()).isEqualTo(UPDATED_LABEL_NAME);
        assertThat(testArticle.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testArticle.getAbstracts()).isEqualTo(UPDATED_ABSTRACTS);
        assertThat(testArticle.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testArticle.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testArticle.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testArticle.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testArticle.getHeadImgUrl()).isEqualTo(UPDATED_HEAD_IMG_URL);
        assertThat(testArticle.getRenderEngine()).isEqualTo(UPDATED_RENDER_ENGINE);
        assertThat(testArticle.getArticleType()).isEqualTo(UPDATED_ARTICLE_TYPE);
        assertThat(testArticle.getReadNum()).isEqualTo(UPDATED_READ_NUM);
        assertThat(testArticle.getCommentNum()).isEqualTo(UPDATED_COMMENT_NUM);
        assertThat(testArticle.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void updateNonExistingArticle() throws Exception {
        int databaseSizeBeforeUpdate = articleRepository.findAll().size();

        // Create the Article

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restArticleMockMvc.perform(put("/api/articles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(article)))
            .andExpect(status().isCreated());

        // Validate the Article in the database
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteArticle() throws Exception {
        // Initialize the database
        articleService.save(article);

        int databaseSizeBeforeDelete = articleRepository.findAll().size();

        // Get the article
        restArticleMockMvc.perform(delete("/api/articles/{id}", article.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Article> articleList = articleRepository.findAll();
        assertThat(articleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Article.class);
    }
}
