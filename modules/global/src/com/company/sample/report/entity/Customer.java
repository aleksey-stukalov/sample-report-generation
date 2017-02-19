package com.company.sample.report.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOGO_ID")
    protected FileDescriptor logo;

    public void setLogo(FileDescriptor logo) {
        this.logo = logo;
    }

    public FileDescriptor getLogo() {
        return logo;
    }


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