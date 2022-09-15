package com.maquk.foodhelperapp.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Weight {
    private Long id;
    private Date date;
    private BigDecimal kilograms;

    public Weight() {

    }

    public Weight(Date date, BigDecimal kilograms) {
        this.date = date;
        this.kilograms = kilograms;
    }

    public Weight(Long id, Date date, BigDecimal kilograms) {
        this.id = id;
        this.date = date;
        this.kilograms = kilograms;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getKilograms() {
        return kilograms;
    }

    public void setKilograms(BigDecimal kilograms) {
        this.kilograms = kilograms;
    }

    @Override
    public String toString() {
        return "Weight{" +
                "id=" + id +
                ", date=" + date +
                ", kilograms=" + kilograms +
                '}';
    }
}
