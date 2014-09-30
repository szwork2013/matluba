package com.shopstuffs.domain;

import javax.persistence.*;

/**
 * Created by jasurbek.umarov on 9/21/2014.
 */
@Entity
@Table(name="image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="image_id")
    private long imageId;

    @Column(name="full_image")
    private String fullImagePath;

    @Column(name="thumbnail_image")
    private String thumbnailImagePath;

    @Column(name="is_main")
    private Boolean isMain;

    @ManyToOne
    private Product productId;


    public Boolean isMain() {
        return isMain;
    }

    public void setIsMain(Boolean isMain) {
        this.isMain = isMain;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
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

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }
}
