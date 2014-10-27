package com.shopstuffs.repository.sort;

import org.springframework.data.domain.Sort;

/**
 * Created by jasurbek.umarov on 10/25/2014.
 */
public class ProductSort {
    public static Sort defaultSort() {
        return new Sort(Sort.Direction.ASC,"title");
    }
}
