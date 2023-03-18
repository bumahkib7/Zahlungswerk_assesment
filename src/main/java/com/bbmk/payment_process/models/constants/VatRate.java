package com.example.zahlungswerk.models.constants;

public enum VatRate {
    ZERO_PERCENT(0.00), SEVEN_PERCENT(0.07), NINETEEN_PERCENT(0.19);

    private final double i;

    VatRate(double i) {
        this.i = i;
    }

     public double getPercentage() {
        return i;
    }

}
