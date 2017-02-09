package com.company.sample.report.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Lob;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "REPORTGENERATION_CUSTOMER")
@Entity(name = "reportgeneration$Customer")
public class Customer extends StandardEntity {
    private static final long serialVersionUID = -1824871719534040978L;

    @Column(name = "NAME", nullable = false)
    protected String name;

    @Lob
    @Column(name = "BILLING_ADDRESS")
    protected String billingAddress;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingAddress() {
        return billingAddress;
    }


}