package org.lightadmin.demo.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

import static com.google.common.collect.Sets.newLinkedHashSet;
import static java.math.BigDecimal.ZERO;
import static javax.persistence.CascadeType.ALL;
import static org.springframework.format.annotation.NumberFormat.Style.CURRENCY;

@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Customer customer;

    @NotBlank
    @Size(max = 255)
    @Column(name = "contact_person")
    private String contactPerson;

    @NotBlank
    @Size(max = 255)
    @Column(name = "contact_phone")
    private String contactPhone;

    @Size(max = 255)
    @Column(name = "contact_email")
    private String contactEmail;

    @Size(max = 255)
    @Column(name = "delivery_address")
    private String deliveryAddress;

    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private Set<LineItem> lineItems = newLinkedHashSet();

    @Size(max = 255)
    private String comment;

    private boolean processed = false;

    public Order() {
    }

    public void addProduct(Product product) {
        final LineItem lineItem = new LineItem(product);
        lineItem.setOrder(this);

        lineItems.add(lineItem);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(Set<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    @NotNull
    @NumberFormat(style = CURRENCY)
    @Column(name = "total_price")
    public BigDecimal getTotalPrice() {
        BigDecimal total = ZERO;
        for (LineItem item : lineItems) {
            total = total.add(item.getPrice());
        }
        return total;
    }
}