package com.shopstuffs.repository.specifications;

import com.shopstuffs.domain.Category;
import com.shopstuffs.domain.Product;
import com.shopstuffs.domain.metamodel.Product_;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import org.springframework.data.jpa.domain.Specification;


/**
 * Created by jasurbek.umarov on 10/25/2014.
 */
public class ProductSpecifications {
  /*  public static Specification<Product> belongsToCategory(String categoryTitle) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery query, CriteriaBuilder cb) {
                EntityType<Product> Product_ = root.getModel();
                Join<Product, Category> category = root.join(Product_.category);
                return cb.equal(root.get(Product_.category), categoryTitle);
            }
        }
    }*/
  public static Specification<Product> hasTitle(String title) {
      return new Specification<Product>() {
          @Override
          public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
              return builder.equal(root.get(Product_.title), title);
          }
      };
  }
}
