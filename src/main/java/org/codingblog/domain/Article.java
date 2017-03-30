package org.codingblog.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Article.
 */
@Entity
@Table(name = "article")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "label_name")
    private String labelName;

    @Column(name = "title")
    private String title;

    @Column(name = "abstracts")
    private String abstracts;

    @Column(name = "create_time")
    private ZonedDateTime createTime;

    @Column(name = "year")
    private Integer year;

    @Column(name = "month")
    private Integer month;

    @Column(name = "day")
    private Integer day;

    @Column(name = "head_img_url")
    private String headImgUrl;

    @Column(name = "render_engine")
    private String renderEngine;

    @Column(name = "article_type")
    private Integer articleType;

    @Column(name = "read_num")
    private Long readNum;

    @Column(name = "comment_num")
    private Long commentNum;

    @Lob
    @Column(name = "content")
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public Article userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLabelName() {
        return labelName;
    }

    public Article labelName(String labelName) {
        this.labelName = labelName;
        return this;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getTitle() {
        return title;
    }

    public Article title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public Article abstracts(String abstracts) {
        this.abstracts = abstracts;
        return this;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public Article createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getYear() {
        return year;
    }

    public Article year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public Article month(Integer month) {
        this.month = month;
        return this;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public Article day(Integer day) {
        this.day = day;
        return this;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public Article headImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
        return this;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getRenderEngine() {
        return renderEngine;
    }

    public Article renderEngine(String renderEngine) {
        this.renderEngine = renderEngine;
        return this;
    }

    public void setRenderEngine(String renderEngine) {
        this.renderEngine = renderEngine;
    }

    public Integer getArticleType() {
        return articleType;
    }

    public Article articleType(Integer articleType) {
        this.articleType = articleType;
        return this;
    }

    public void setArticleType(Integer articleType) {
        this.articleType = articleType;
    }

    public Long getReadNum() {
        return readNum;
    }

    public Article readNum(Long readNum) {
        this.readNum = readNum;
        return this;
    }

    public void setReadNum(Long readNum) {
        this.readNum = readNum;
    }

    public Long getCommentNum() {
        return commentNum;
    }

    public Article commentNum(Long commentNum) {
        this.commentNum = commentNum;
        return this;
    }

    public void setCommentNum(Long commentNum) {
        this.commentNum = commentNum;
    }

    public String getContent() {
        return content;
    }

    public Article content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Article article = (Article) o;
        if (article.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Article{" +
            "id=" + id +
            ", userId='" + userId + "'" +
            ", labelName='" + labelName + "'" +
            ", title='" + title + "'" +
            ", abstracts='" + abstracts + "'" +
            ", createTime='" + createTime + "'" +
            ", year='" + year + "'" +
            ", month='" + month + "'" +
            ", day='" + day + "'" +
            ", headImgUrl='" + headImgUrl + "'" +
            ", renderEngine='" + renderEngine + "'" +
            ", articleType='" + articleType + "'" +
            ", readNum='" + readNum + "'" +
            ", commentNum='" + commentNum + "'" +
            ", content='" + content + "'" +
            '}';
    }
}
