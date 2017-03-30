package org.codingblog.service.impl;

import org.codingblog.service.GalleryService;
import org.codingblog.domain.Gallery;
import org.codingblog.repository.GalleryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Gallery.
 */
@Service
@Transactional
public class GalleryServiceImpl implements GalleryService{

    private final Logger log = LoggerFactory.getLogger(GalleryServiceImpl.class);
    
    private final GalleryRepository galleryRepository;

    public GalleryServiceImpl(GalleryRepository galleryRepository) {
        this.galleryRepository = galleryRepository;
    }

    /**
     * Save a gallery.
     *
     * @param gallery the entity to save
     * @return the persisted entity
     */
    @Override
    public Gallery save(Gallery gallery) {
        log.debug("Request to save Gallery : {}", gallery);
        Gallery result = galleryRepository.save(gallery);
        return result;
    }

    /**
     *  Get all the galleries.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Gallery> findAll(Pageable pageable) {
        log.debug("Request to get all Galleries");
        Page<Gallery> result = galleryRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one gallery by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Gallery findOne(Long id) {
        log.debug("Request to get Gallery : {}", id);
        Gallery gallery = galleryRepository.findOne(id);
        return gallery;
    }

    /**
     *  Delete the  gallery by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Gallery : {}", id);
        galleryRepository.delete(id);
    }
}
