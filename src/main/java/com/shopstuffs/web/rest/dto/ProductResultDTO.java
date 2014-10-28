package com.shopstuffs.web.rest.dto;

import com.shopstuffs.domain.Product;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jasurbek.umarov on 10/26/2014.
 */
public class ProductResultDTO implements Serializable { public ProductResultDTO(){}
    public List<Product> products;
    public Integer pages;
    public Integer index;

    public ProductResultDTO(List<Product>products, Integer totalPages, Integer pageIndex){
        this.products = products;
        this.pages = totalPages;
        this.index = pageIndex;
    }

}
