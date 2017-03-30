package org.codingblog.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Gallery.
 */
@Entity
@Table(name = "gallery")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Gallery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "label_name")
    private String labelName;

    @Column(name = "category")
    private String category;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "short_msg")
    private String shortMsg;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "resource_type")
    private Integer resourceType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public Gallery userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLabelName() {
        return labelName;
    }

    public Gallery labelName(String labelName) {
        this.labelName = labelName;
        return this;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getCategory() {
        return category;
    }

    public Gallery category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Gallery imgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getShortMsg() {
        return shortMsg;
    }

    public Gallery shortMsg(String shortMsg) {
        this.shortMsg = shortMsg;
        return this;
    }

    public void setShortMsg(String shortMsg) {
        this.shortMsg = shortMsg;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Gallery createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public Gallery resourceType(Integer resourceType) {
        this.resourceType = resourceType;
        return this;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Gallery gallery = (Gallery) o;
        if (gallery.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, gallery.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Gallery{" +
            "id=" + id +
            ", userId='" + userId + "'" +
            ", labelName='" + labelName + "'" +
            ", category='" + category + "'" +
            ", imgUrl='" + imgUrl + "'" +
            ", shortMsg='" + shortMsg + "'" +
            ", createTime='" + createTime + "'" +
            ", resourceType='" + resourceType + "'" +
            '}';
    }
}
