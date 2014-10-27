package com.shopstuffs.repository.specifications;

import com.shopstuffs.domain.Category;
import com.shopstuffs.domain.Category_;
import com.shopstuffs.domain.Product;
import com.shopstuffs.domain.Product_;

import javax.persistence.criteria.*;

import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;


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
              return builder.like(root.get(Product_.title), getLikePattern(title));
          }

          private String getLikePattern(final String searchTerm) {
              StringBuilder pattern = new StringBuilder();
              pattern.append(searchTerm.toLowerCase());
              pattern.append("%");
              return pattern.toString();
          }
      };
  }

  public static Specification<Product> inCategory(String categName) {
       return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                final Path<Category> category = root.get(Product_.category);
                final Path<String> name = category.get(Category_.name);
                return builder.equal(name, categName);

            }
        };
  }

//    public static Specification<Product> betweenDates(DateTime from, DateTime to) {
//        return new Specification<Product>() {
//            @Override
//            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
//                builder.between(root.get(Product_.releaseDate)
//                if (from != null && to == null) {
//                    return builder.equal(root.get(Product_.releaseDate) , from);
//                }
//
//                if (from == null && to != null) {
//                    return builder.equal(root.get(Product_.expireDate) , to);
//                }
//
//
//
//                return builder
//
//                        .greaterThanOrEqualTo(root.get(Product_.expireDate) , from).
//
//                        .and(root.get(Product_.expireDate) , from);
//            }
//
//            private String getLikePattern(final String searchTerm) {
//                StringBuilder pattern = new StringBuilder();
//                pattern.append(searchTerm.toLowerCase());
//                pattern.append("%");
//                return pattern.toString();
//            }
//        };
//    }

    public static Specification<Product> hasPrice(BigDecimal price) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.equal(root.get(Product_.price), price);
            }
        };
    }

    public static Specification<Product> hasRentalPrice(BigDecimal price) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.equal(root.get(Product_.rentalPrice), price);
            }
        };
    }

}
