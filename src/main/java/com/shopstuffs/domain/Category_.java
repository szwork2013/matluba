package com.shopstuffs.domain;

import org.joda.time.DateTime;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;

/**
 * Created by jasurbek.umarov on 10/26/2014.
 */
@StaticMetamodel(Category.class)
public class Category_ {
    public static volatile SingularAttribute<Category, String> name;
}
