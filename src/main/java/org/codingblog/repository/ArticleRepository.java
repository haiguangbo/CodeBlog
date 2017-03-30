package org.codingblog.repository;

import org.codingblog.domain.Article;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Article entity.
 */
@SuppressWarnings("unused")
public interface ArticleRepository extends JpaRepository<Article,Long> {

}
