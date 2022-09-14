package com.maquk.foodhelperapp.pojo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Water {
    private LocalDate date;
    private BigDecimal milliliters;

    public Water(LocalDate date, BigDecimal milliliters) {
        this.date = date;
        this.milliliters = milliliters;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getMilliliters() {
        return milliliters;
    }

    public void setMilliliters(BigDecimal milliliters) {
        this.milliliters = milliliters;
    }
}
