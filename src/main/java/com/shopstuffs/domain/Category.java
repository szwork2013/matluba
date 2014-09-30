package com.shopstuffs.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * User: Jama
 * Date: 9/21/2014
 */
@Entity
@Table(name = "category")
public class Category  implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private long id;
    private String name;

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    private Collection<Product> products;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }
}
