package org.codingblog.repository;

import org.codingblog.domain.Gallery;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Gallery entity.
 */
@SuppressWarnings("unused")
public interface GalleryRepository extends JpaRepository<Gallery,Long> {

}
