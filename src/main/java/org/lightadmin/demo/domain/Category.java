package org.lightadmin.demo.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.lightadmin.api.config.annotation.FileReference;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Sets.newLinkedHashSet;

@Entity
@Table(name = "categories")
public class Category extends AbstractEntity {

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @Size(max = 255)
    private String title;

    @Lob
    private String description;

    @Column(name = "order_idx")
    private int orderIdx = 0;

    @Column(name = "image_url")
    @Size(max = 255)
    @FileReference(baseDirectory = "/Users/max/Desktop/hubbery")
    private String image;

    @ManyToMany
    @JoinTable(name = "categories_tags",
            joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "ID")
    )
    private Set<Tag> tags = newLinkedHashSet();

    @OneToMany
    @JoinColumn(name = "parent_category_id")
    private List<Category> children = newLinkedList();

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private Category parent;

    @NotBlank
    @Size(max = 50)
    @Column(name = "url_name")
    private String urlName;

    @Lob
    @Column(name = "meta_description")
    private String metaDescription;

    @Size(max = 255)
    @Column(name = "meta_keywords")
    private String metaKeywords;

    @Lob
    @Column(name = "spam_text")
    private String spamText;

    private boolean published = false;

    public Category() {
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrderIdx() {
        return orderIdx;
    }

    public void setOrderIdx(int orderIdx) {
        this.orderIdx = orderIdx;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public String getSpamText() {
        return spamText;
    }

    public void setSpamText(String spamText) {
        this.spamText = spamText;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}