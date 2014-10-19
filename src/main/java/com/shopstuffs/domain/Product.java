package com.shopstuffs.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.validation.constraints.*;

/**
 * A Product.
 */
@Entity
@Table(name = "T_PRODUCT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @NotNull
    @Size(min = 2)
    private String title;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "product_type")
    private ProductType productType;

    @Column(name = "old_price")
    private BigDecimal oldPrice;

    @ManyToOne
    private Category category;

    @Column(name = "retail_price")
    @DecimalMin(value = "1.00")
    private BigDecimal price;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Attribute> attributes = new ArrayList<>();

    @Column(name = "rental_price")
    private BigDecimal rentalPrice;

    @OneToMany
    private List<Image> images = new ArrayList<>();

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "release_date")
    private DateTime releaseDate = DateTime.now();

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "expire_date")
    private DateTime expireDate = DateTime.now();


    private String description;


















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

        Product product = (Product) o;

        if (!id.equals(product.id)) {
            return false;
        }

        return true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(BigDecimal oldPrice) {
        this.oldPrice = oldPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Collection<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public BigDecimal getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(BigDecimal rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public Collection<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public DateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(DateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public DateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(DateTime expireDate) {
        this.expireDate = expireDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", productType='" + productType + '\'' +
                ", oldPrice=" + oldPrice +
                ", price=" + price +
                ", attributes=" + attributes +
                ", rentalPrice=" + rentalPrice +
                ", images=" + images +
                '}';
    }
}
