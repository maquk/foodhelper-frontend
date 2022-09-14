package com.maquk.foodhelperapp.pojo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Weight {
    private LocalDate date;
    private BigDecimal kilograms;

    public Weight(LocalDate date, BigDecimal kilograms) {
        this.date = date;
        this.kilograms = kilograms;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getKilograms() {
        return kilograms;
    }

    public void setKilograms(BigDecimal kilograms) {
        this.kilograms = kilograms;
    }
}
