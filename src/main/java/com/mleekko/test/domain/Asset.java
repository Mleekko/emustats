package com.mleekko.test.domain;

import java.math.BigDecimal;

/**
 * @author Mleekko
 */
public class Asset {
    private String iso;
    private String label;
    private BigDecimal amount;

    public Asset() {
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
