package com.shopstuffs.domain.metamodel;
/**
 * Created by jasurbek.umarov on 10/25/2014.
 */


import com.shopstuffs.domain.*;
import org.joda.time.DateTime;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;

@StaticMetamodel(Product.class)
public class Product_ {
    public static volatile SingularAttribute<Product, Long> id;
    public static volatile SingularAttribute<Product, String> title;
    public static volatile SingularAttribute<Product, ProductType> productType;
    public static volatile SingularAttribute<Product, Category> category;
    public static volatile SingularAttribute<Product, BigDecimal> oldPrice;
    public static volatile SingularAttribute<Product, BigDecimal> price;
    public static volatile SingularAttribute<Product, BigDecimal> rentalPrice;
    public static volatile SingularAttribute<Product, String> description;
    public static volatile SingularAttribute<Product, DateTime> expireDate;
    public static volatile SingularAttribute<Product, DateTime> releaseDate;
    public static volatile ListAttribute<Product, Attribute> attributes;
    public static volatile ListAttribute<Product, Image> images;

}
