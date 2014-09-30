package com.shopstuffs.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;
import com.shopstuffs.domain.util.CustomLocalDateSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A Attribute.
 */
@Entity
@Table(name = "T_ATTRIBUTE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Attribute implements Serializable {

    @Id
    @Column(name = "attribute_id")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(name = "attribute_name")
    private String attributeName;
    @Column(name = "attribute_value")
    private String attributeValue;

    @ManyToOne
    private Product productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Attribute attribute = (Attribute) o;

        if (!id.equals(attribute.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "id=" + id +
                ", attributeName='" + attributeName + '\'' +
                ", attributeValue='" + attributeValue + '\'' +
                ", productId=" + productId +
                '}';
    }
}
