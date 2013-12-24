package org.lightadmin.demo.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "colors")
public class Color extends AbstractEntity {

    @NotBlank
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    @Column(name = "original_name")
    private String originalName;

    @Lob
    @Basic
    private byte[] image;

    public Color() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}