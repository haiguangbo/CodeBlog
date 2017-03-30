package org.codingblog.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Gallery entity.
 */
public class GalleryDTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GalleryDTO galleryDTO = (GalleryDTO) o;

        if ( ! Objects.equals(id, galleryDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GalleryDTO{" +
            "id=" + id +
            '}';
    }
}
