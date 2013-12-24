package org.lightadmin.demo.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static org.springframework.format.annotation.NumberFormat.Style.CURRENCY;

@Entity
@Table(name = "line_items")
public class LineItem extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    @NumberFormat(style = CURRENCY)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public LineItem() {
    }

    public LineItem(Product product) {
        this.product = product;
        this.price = product.getPrice();
    }

    @JsonIgnore
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @JsonIgnore
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}