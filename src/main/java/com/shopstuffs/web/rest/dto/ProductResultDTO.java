package com.shopstuffs.web.rest.dto;

import com.shopstuffs.domain.Product;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jasurbek.umarov on 10/26/2014.
 */
public class ProductResultDTO implements Serializable { public ProductResultDTO(){}
    public List<Product> Products;
    public Integer TotalPages;
    public Integer PageIndex;

    public ProductResultDTO(List<Product>products, Integer totalPages, Integer pageIndex){
        Products = products;
        TotalPages = totalPages;
        PageIndex = pageIndex;
    }

}
