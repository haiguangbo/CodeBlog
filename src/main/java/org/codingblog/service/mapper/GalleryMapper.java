package org.codingblog.service.mapper;

import org.codingblog.domain.*;
import org.codingblog.service.dto.GalleryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Gallery and its DTO GalleryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GalleryMapper {

    GalleryDTO galleryToGalleryDTO(Gallery gallery);

    List<GalleryDTO> galleriesToGalleryDTOs(List<Gallery> galleries);

    Gallery galleryDTOToGallery(GalleryDTO galleryDTO);

    List<Gallery> galleryDTOsToGalleries(List<GalleryDTO> galleryDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Gallery galleryFromId(Long id) {
        if (id == null) {
            return null;
        }
        Gallery gallery = new Gallery();
        gallery.setId(id);
        return gallery;
    }
    

}
