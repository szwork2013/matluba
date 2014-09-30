package com.shopstuffs.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

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

    private String title;
    private String description;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "old_price")
    private BigDecimal oldPrice;

    @Column(name = "retail_price")
    private BigDecimal price;

    @OneToMany(mappedBy = "productId", fetch = FetchType.LAZY)
    private Collection<Attribute> attributes;

    @ManyToOne
    private Category category;

    @Column(name = "rental_price")
    private BigDecimal rentalPrice;

    @OneToMany(mappedBy = "productId")
    private Collection<Image> images;

//    @NotNull
//    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonSerialize(using = CustomLocalDateSerializer.class)
//    @Column(name = "sample_date_attribute", nullable = false)
//    private LocalDate sampleDateAttribute;

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

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
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

    public void setAttributes(Collection<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public void setImages(Collection<Image> images) {
        this.images = images;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
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
                ", category=" + category +
                ", rentalPrice=" + rentalPrice +
                ", images=" + images +
                '}';
    }
}
