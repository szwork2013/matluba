package com.shopstuffs.web.rest.dto;

import com.shopstuffs.domain.Product;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by jasurbek.umarov on 10/26/2014.
 */
public class ProductCriteriaDTO { public ProductCriteriaDTO(){}

    private String title;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    private String categoryName;
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    private BigDecimal price;
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    private DateTime releaseDate;
    public DateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(DateTime releaseDate) {
        this.releaseDate = releaseDate;
    }


    private DateTime expireDate;
    public DateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(DateTime expireDate) {
        this.expireDate = expireDate;
    }




}
