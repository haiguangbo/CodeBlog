package org.codingblog.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.codingblog.domain.Article;
import org.codingblog.service.ArticleService;
import org.codingblog.web.rest.util.HeaderUtil;
import org.codingblog.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Article.
 */
@RestController
@RequestMapping("/api")
public class ArticleResource {

    private final Logger log = LoggerFactory.getLogger(ArticleResource.class);

    private static final String ENTITY_NAME = "article";
        
    private final ArticleService articleService;

    public ArticleResource(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * POST  /articles : Create a new article.
     *
     * @param article the article to create
     * @return the ResponseEntity with status 201 (Created) and with body the new article, or with status 400 (Bad Request) if the article has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/articles")
    @Timed
    public ResponseEntity<Article> createArticle(@RequestBody Article article) throws URISyntaxException {
        log.debug("REST request to save Article : {}", article);
        if (article.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new article cannot already have an ID")).body(null);
        }
        Article result = articleService.save(article);
        return ResponseEntity.created(new URI("/api/articles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /articles : Updates an existing article.
     *
     * @param article the article to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated article,
     * or with status 400 (Bad Request) if the article is not valid,
     * or with status 500 (Internal Server Error) if the article couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/articles")
    @Timed
    public ResponseEntity<Article> updateArticle(@RequestBody Article article) throws URISyntaxException {
        log.debug("REST request to update Article : {}", article);
        if (article.getId() == null) {
            return createArticle(article);
        }
        Article result = articleService.save(article);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, article.getId().toString()))
            .body(result);
    }

    /**
     * GET  /articles : get all the articles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of articles in body
     */
    @GetMapping("/articles")
    @Timed
    public ResponseEntity<List<Article>> getAllArticles(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Articles");
        Page<Article> page = articleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/articles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /articles/:id : get the "id" article.
     *
     * @param id the id of the article to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the article, or with status 404 (Not Found)
     */
    @GetMapping("/articles/{id}")
    @Timed
    public ResponseEntity<Article> getArticle(@PathVariable Long id) {
        log.debug("REST request to get Article : {}", id);
        Article article = articleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(article));
    }

    /**
     * DELETE  /articles/:id : delete the "id" article.
     *
     * @param id the id of the article to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/articles/{id}")
    @Timed
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        log.debug("REST request to delete Article : {}", id);
        articleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
