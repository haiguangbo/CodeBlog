package org.codingblog.service;

import org.codingblog.domain.Gallery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Gallery.
 */
public interface GalleryService {

    /**
     * Save a gallery.
     *
     * @param gallery the entity to save
     * @return the persisted entity
     */
    Gallery save(Gallery gallery);

    /**
     *  Get all the galleries.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Gallery> findAll(Pageable pageable);

    /**
     *  Get the "id" gallery.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Gallery findOne(Long id);

    /**
     *  Delete the "id" gallery.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
