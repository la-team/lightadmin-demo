package org.lightadmin.demo.administration;

import org.lightadmin.demo.domain.Category;
import org.lightadmin.demo.domain.MenuItem;
import org.lightadmin.api.config.AdministrationConfiguration;
import org.lightadmin.api.config.builder.*;
import org.lightadmin.api.config.unit.EntityMetadataConfigurationUnit;
import org.lightadmin.api.config.unit.FieldSetConfigurationUnit;
import org.lightadmin.api.config.unit.FiltersConfigurationUnit;
import org.lightadmin.api.config.unit.ScopesConfigurationUnit;
import org.lightadmin.api.config.utils.DomainTypeSpecification;
import org.lightadmin.demo.domain.Category;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static org.lightadmin.api.config.utils.EnumElement.element;
import static org.lightadmin.api.config.utils.ScopeMetadataUtils.all;
import static org.lightadmin.api.config.utils.ScopeMetadataUtils.specification;

@SuppressWarnings("unused")
public class MenuItemAdministration extends AdministrationConfiguration<MenuItem> {

    @Override
    public EntityMetadataConfigurationUnit configuration(EntityMetadataConfigurationUnitBuilder configurationBuilder) {
        return configurationBuilder.pluralName("Navigation").singularName("Menu Item").nameField("name").build();
    }

    @Override
    public FieldSetConfigurationUnit listView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("orderIdx").caption("Order")
                .field("menuType").caption("Type")
                .field("name").caption("Name")
                .field("url").caption("Url")
                .field("published").caption("Published")
                .build();
    }

    @Override
    public FieldSetConfigurationUnit quickView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("name").caption("Name")
                .field("title").caption("Title")
                .field("url").caption("Url")
                .field("image").caption("Image")
                .field("children").caption("Children")
                .field("published").caption("Published")
                .build();
    }

    @Override
    public FieldSetConfigurationUnit showView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("orderIdx").caption("Order")
                .field("menuType").caption("Type")
                .field("name").caption("Name")
                .field("title").caption("Title")
                .field("url").caption("Url")
                .field("image").caption("Image")
                .field("children").caption("Children")
                .field("published").caption("Published")
                .build();
    }

    @Override
    public FieldSetConfigurationUnit formView(PersistentFieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("orderIdx").caption("Order")
                .field("menuType").caption("Type").enumeration(
                        element("LeftMenu", "Left Menu"),
                        element("TopMenu", "Top Menu")
                )
                .field("name").caption("Name")
                .field("title").caption("Title")
                .field("url").caption("Url")
                .field("image").caption("Image")
                .field("children").caption("Children")
                .field("published").caption("Published")
                .build();
    }

    @Override
    public FiltersConfigurationUnit filters(FiltersConfigurationUnitBuilder filterBuilder) {
        return filterBuilder
                .filter("Name", "name")
                .filter("Child Menu", "children")
                .filter("Published", "published")
                .build();
    }

    @Override
    public ScopesConfigurationUnit scopes(ScopesConfigurationUnitBuilder scopeBuilder) {
        return scopeBuilder
                .scope("All", all()).defaultScope()
                .scope("Left menu", specification(menuItemByTypeSpec("LeftMenu")))
                .scope("Top menu", specification(menuItemByTypeSpec("TopMenu")))
                .build();
    }

    private DomainTypeSpecification<Category> menuItemByTypeSpec(final String menuType) {
        return new DomainTypeSpecification<Category>() {
            @Override
            public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("menuType"), menuType);
            }
        };
    }
}