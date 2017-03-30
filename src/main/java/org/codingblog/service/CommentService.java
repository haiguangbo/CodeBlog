package org.codingblog.service;

import org.codingblog.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Comment.
 */
public interface CommentService {

    /**
     * Save a comment.
     *
     * @param comment the entity to save
     * @return the persisted entity
     */
    Comment save(Comment comment);

    /**
     *  Get all the comments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Comment> findAll(Pageable pageable);

    /**
     *  Get the "id" comment.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Comment findOne(Long id);

    /**
     *  Delete the "id" comment.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
