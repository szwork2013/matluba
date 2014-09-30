package com.shopstuffs.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: Jama
 * Date: 9/21/2014
 */
@Entity
@Table(name = "attribute")
public class Attribute implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "attribute_id")
    private long id;

    @Column(name = "attribute_name")
    private String attributeName;
    @Column(name = "attribute_value")
    private String attributeValue;

    @ManyToOne
    private Product productId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attribute_name) {
        this.attributeName = attribute_name;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attribute_value) {
        this.attributeValue = attribute_value;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product product) {
        this.productId = product;
    }
}
