package com.company.sample.report.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@NamePattern("%s|name")
@Table(name = "REPORTGENERATION_PRODUCT")
@Entity(name = "reportgeneration$Product")
public class Product extends StandardEntity {
    private static final long serialVersionUID = 8833420447166254432L;

    @Column(name = "NAME", nullable = false)
    protected String name;

    @Column(name = "PRICE", nullable = false)
    protected BigDecimal price;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }


}