package org.lightadmin.demo.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.lightadmin.api.config.annotation.FileReference;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "product_photos")
public class ProductPhoto extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @NotBlank
    @Column(name = "image_url")
    @Size(max = 255)
    @FileReference(baseDirectory = "/file-storage/lightadmin-demo")
    private String image;

    @Column(name = "main_photo")
    private boolean main;

    public ProductPhoto() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }
}