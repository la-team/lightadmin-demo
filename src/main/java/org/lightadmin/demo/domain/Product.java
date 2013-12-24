package org.lightadmin.demo.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

import static com.google.common.collect.Sets.newLinkedHashSet;
import static javax.persistence.CascadeType.ALL;
import static org.springframework.format.annotation.NumberFormat.Style.CURRENCY;

@Entity
@Table(name = "products")
public class Product extends AbstractEntity {

    @NotBlank
    @Size(max = 255)
    private String title;

    @NotBlank
    @Size(max = 255)
    private String brand;

    @NotBlank
    @Size(max = 255)
    @Column(name = "product_code")
    private String productCode;

    @Size(max = 255)
    @Column(name = "manufacturer_code")
    private String manufacturerCode;

    @Size(max = 255)
    private String manufacturer;

    @Basic
    private int guarantee = 12;

    @NotBlank
    @Column(name = "sex")
    private String gender = "Unisex";

    @Column(name = "dimension_length")
    private Integer dimensionLength;

    @Column(name = "dimension_width")
    private Integer dimensionWidth;

    @Column(name = "dimension_height")
    private Integer dimensionHeight;

    @Column(name = "dimension_weight")
    private Integer dimensionWeight;

    @NotNull
    @NumberFormat(style = CURRENCY)
    private BigDecimal price;

    @Lob
    private String description;

    private boolean published;

    private String availability;

    @OneToMany(cascade = ALL)
    @JoinColumn(name = "product_id")
    private Set<ProductPhoto> productPhotos;

    @OneToMany(cascade = ALL)
    @JoinColumn(name = "product_id")
    private Set<ProductVideo> productVideos;

    @ManyToMany
    @JoinTable(name = "colors_products",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "color_id", referencedColumnName = "ID")
    )
    private Set<Color> colors = newLinkedHashSet();

    @ManyToMany
    @JoinTable(name = "products_tags",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "ID")
    )
    private Set<Tag> tags = newLinkedHashSet();

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @ManyToOne
    @JoinColumn(name = "collection_id")
    private Collection collection;

    public Product() {
    }

    public Set<ProductPhoto> getProductPhotos() {
        return productPhotos;
    }

    @Transient
    public ProductPhoto getMainProductPhoto() {
        Set<ProductPhoto> productPhotos = getProductPhotos();
        for (ProductPhoto productPhoto : productPhotos) {
            if (productPhoto.isMain()) {
                return productPhoto;
            }
        }
        return null;
    }

    public void setProductPhotos(Set<ProductPhoto> productPhotos) {
        this.productPhotos = productPhotos;
    }

    public Set<ProductVideo> getProductVideos() {
        return productVideos;
    }

    public void setProductVideos(Set<ProductVideo> productVideos) {
        this.productVideos = productVideos;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Color> getColors() {
        return colors;
    }

    public void setColors(Set<Color> colors) {
        this.colors = colors;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getManufacturerCode() {
        return manufacturerCode;
    }

    public void setManufacturerCode(String manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(int guarantee) {
        this.guarantee = guarantee;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getDimensionLength() {
        return dimensionLength;
    }

    public void setDimensionLength(int dimensionLength) {
        this.dimensionLength = dimensionLength;
    }

    public int getDimensionWidth() {
        return dimensionWidth;
    }

    public void setDimensionWidth(int dimensionWidth) {
        this.dimensionWidth = dimensionWidth;
    }

    public int getDimensionHeight() {
        return dimensionHeight;
    }

    public void setDimensionHeight(int dimensionHeight) {
        this.dimensionHeight = dimensionHeight;
    }

    public int getDimensionWeight() {
        return dimensionWeight;
    }

    public void setDimensionWeight(int dimensionWeight) {
        this.dimensionWeight = dimensionWeight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }
}