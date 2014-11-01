package com.shopstuffs.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Image.
 */
@Entity
@Table(name = "T_IMAGE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Image implements Serializable {

    public static final Image EMPTY = new Image();

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "image_id")
    private Long id;

    @Column(name = "full_image")
    private String fullImagePath;

    @Column(name = "thumbnail_image")
    private String thumbnailImagePath;

    @Column(name = "is_main")
    private Boolean isMain;

    @ManyToOne
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullImagePath() {
        return fullImagePath;
    }

    public void setFullImagePath(String fullImagePath) {
        this.fullImagePath = fullImagePath;
    }

    public String getThumbnailImagePath() {
        return thumbnailImagePath;
    }

    public void setThumbnailImagePath(String thumbnailImagePath) {
        this.thumbnailImagePath = thumbnailImagePath;
    }

    public Boolean getIsMain() {
        return isMain;
    }

    public void setIsMain(Boolean isMain) {
        this.isMain = isMain;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product productId) {
        this.product = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Image image = (Image) o;

        return id.equals(image.id);

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", fullImagePath='" + fullImagePath + '\'' +
                ", thumbnailImagePath='" + thumbnailImagePath + '\'' +
                ", isMain=" + isMain +
                '}';
    }
}
