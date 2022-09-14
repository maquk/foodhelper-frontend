package com.maquk.foodhelperapp.pojo;

import java.math.BigDecimal;

public class ProductConsumed {

    private Product product;
    private BigDecimal grams;

    public ProductConsumed(Product product, BigDecimal grams) {
        this.product = product;
        this.grams = grams;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getGrams() {
        return grams;
    }

    public void setGrams(BigDecimal grams) {
        this.grams = grams;
    }

    @Override
    public String toString() {
        return "ProductConsumed{" +
                "product=" + product +
                ", grams=" + grams +
                '}';
    }
}
