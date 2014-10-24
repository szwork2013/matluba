package com.shopstuffs.web.rest.dto;

/**
 * Created by jasurbek.umarov on 10/23/2014.
 */
public class ProductAttributeDTO {

    private Long productId;

    private Long attributeId;


    public ProductAttributeDTO() {
    }

    public ProductAttributeDTO(Long productId, Long attributeId) {
        this.productId = productId;
        this.attributeId = attributeId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getAttributeId() {
        return attributeId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProductAttribute{");
        sb.append("productId='").append(productId).append('\'');
        sb.append(", attributeId='").append(attributeId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

