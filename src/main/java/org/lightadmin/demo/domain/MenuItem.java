package org.lightadmin.demo.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.lightadmin.api.config.annotation.FileReference;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

import static com.google.common.collect.Sets.newLinkedHashSet;

@Entity
@Table(name = "menu_items")
public class MenuItem extends AbstractEntity {

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    private String title;

    @Column(name = "menu_type")
    private String menuType;

    @Column(name = "order_idx")
    private int orderIdx = 0;

    private String url;

    @Column(name = "image_url")
    @FileReference(baseDirectory = "/Users/max/Desktop/hubbery")
    private String image;

    private boolean published;

    @OneToMany
    @JoinColumn(name = "parent_menu_item_id")
    private Set<MenuItem> children = newLinkedHashSet();

    @ManyToOne
    @JoinColumn(name = "parent_menu_item_id", insertable = false, updatable = false)
    private MenuItem parent;

    public MenuItem() {
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

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public int getOrderIdx() {
        return orderIdx;
    }

    public void setOrderIdx(int orderIdx) {
        this.orderIdx = orderIdx;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Set<MenuItem> getChildren() {
        return children;
    }

    public void setChildren(Set<MenuItem> children) {
        this.children = children;
    }

    public MenuItem getParent() {
        return parent;
    }

    public void setParent(MenuItem parent) {
        this.parent = parent;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}