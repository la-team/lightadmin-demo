package org.lightadmin.demo.administration;

import org.lightadmin.demo.domain.Category;
import org.lightadmin.api.config.AdministrationConfiguration;
import org.lightadmin.api.config.builder.*;
import org.lightadmin.api.config.unit.*;
import org.lightadmin.api.config.utils.DomainTypeSpecification;
import org.lightadmin.demo.domain.Category;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static org.lightadmin.api.config.utils.Editors.textArea;
import static org.lightadmin.api.config.utils.ScopeMetadataUtils.all;
import static org.lightadmin.api.config.utils.ScopeMetadataUtils.specification;

@SuppressWarnings("unused")
public class CategoryAdministration extends AdministrationConfiguration<Category> {

    @Override
    public EntityMetadataConfigurationUnit configuration(EntityMetadataConfigurationUnitBuilder configurationBuilder) {
        return configurationBuilder.pluralName("Categories").singularName("Category").nameField("name").build();
    }

    @Override
    public ScreenContextConfigurationUnit screenContext(ScreenContextConfigurationUnitBuilder screenContextBuilder) {
        return screenContextBuilder.screenName("Categories Administration").build();
    }

    @Override
    public FieldSetConfigurationUnit listView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("orderIdx").caption("Order")
                .field("name").caption("Name")
                .field("urlName").caption("Url Fragment")
                .field("children").caption("Children")
                .field("published").caption("Published")
                .build();
    }

    @Override
    public FieldSetConfigurationUnit quickView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("name").caption("Name")
                .field("title").caption("Title")
                .field("description").caption("Description")
                .field("image").caption("Picture")
                .field("children").caption("Children")
                .field("tags").caption("Tags")
                .build();
    }

    @Override
    public FieldSetConfigurationUnit showView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("orderIdx").caption("Order")
                .field("name").caption("Name")
                .field("title").caption("Title")
                .field("urlName").caption("Url Fragment")
                .field("image").caption("Picture")
                .field("description").caption("Description")
                .field("children").caption("Children")
                .field("tags").caption("Tags")
                .field("published").caption("Published")
                .field("metaDescription").caption("Meta Description")
                .field("metaKeywords").caption("Meta Keywords")
                .field("spamText").caption("Spam Text")
                .build();
    }

    @Override
    public FieldSetConfigurationUnit formView(PersistentFieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("orderIdx").caption("Order")
                .field("name").caption("Name")
                .field("urlName").caption("Url Fragment")
                .field("title").caption("Title").editor(textArea())
                .field("description").caption("Description").editor(textArea())
                .field("image").caption("Picture")
                .field("published").caption("Published")
                .field("children").caption("Children")
                .field("tags").caption("Tags")
                .field("metaDescription").caption("Meta Description").editor(textArea())
                .field("metaKeywords").caption("Meta Keywords").editor(textArea())
                .field("spamText").caption("Spam Text").editor(textArea())
                .build();
    }

    @Override
    public FiltersConfigurationUnit filters(FiltersConfigurationUnitBuilder filterBuilder) {
        return filterBuilder
                .filter("Name", "name")
                .filter("Title", "title")
                .filter("Parent Menu", "parent")
                .filter("Child Menu", "children")
                .filter("Tags", "tags")
                .filter("Published", "published")
                .build();
    }

    @Override
    public ScopesConfigurationUnit scopes(ScopesConfigurationUnitBuilder scopeBuilder) {
        return scopeBuilder.scope("All", all()).defaultScope()
                .scope("Root categories", specification(rootCategoriesSpec()))
                .build();
    }

    private DomainTypeSpecification<Category> rootCategoriesSpec() {
        return new DomainTypeSpecification<Category>() {
            @Override
            public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.isNull(root.get("parent"));
            }
        };
    }
}