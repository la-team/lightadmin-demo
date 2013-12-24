package org.lightadmin.demo.administration;

import org.lightadmin.api.config.AdministrationConfiguration;
import org.lightadmin.api.config.builder.*;
import org.lightadmin.api.config.unit.EntityMetadataConfigurationUnit;
import org.lightadmin.api.config.unit.FieldSetConfigurationUnit;
import org.lightadmin.api.config.unit.FiltersConfigurationUnit;
import org.lightadmin.api.config.unit.ScopesConfigurationUnit;
import org.lightadmin.api.config.utils.DomainTypeSpecification;
import org.lightadmin.demo.domain.Order;
import org.lightadmin.demo.domain.Product;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static org.lightadmin.api.config.utils.Editors.enumeration;
import static org.lightadmin.api.config.utils.Editors.wysiwyg;
import static org.lightadmin.api.config.utils.ScopeMetadataUtils.all;
import static org.lightadmin.api.config.utils.ScopeMetadataUtils.specification;

@SuppressWarnings("unused")
public class ProductAdministration extends AdministrationConfiguration<Product> {

    @Override
    public EntityMetadataConfigurationUnit configuration(EntityMetadataConfigurationUnitBuilder configurationBuilder) {
        return configurationBuilder.pluralName("Products").singularName("Product").nameField("title").build();
    }

    @Override
    public FieldSetConfigurationUnit listView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("productCode").caption("Code")
                .field("collection").caption("Collection")
                .field("title").caption("Title")
                .field("gender").caption("Gender")
                .field("price").caption("Price")
                .field("published").caption("Published")
                .build();
    }

    @Override
    public FieldSetConfigurationUnit quickView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("manufacturer").caption("Manufacturer")
                .field("manufacturerCode").caption("Manufacturer Code")
                .field("material").caption("Material")
                .field("colors").caption("Colors")
                .field("availability").caption("Availability")
                .field("dimensionLength").caption("Length")
                .field("dimensionWidth").caption("Width")
                .field("dimensionHeight").caption("Height")
                .field("dimensionWeight").caption("Weight")
                .field("description").caption("Description")
                .build();
    }

    @Override
    public FieldSetConfigurationUnit showView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("title").caption("Title")
                .field("brand").caption("Brand")
                .field("productCode").caption("Code")
                .field("manufacturer").caption("Manufacturer")
                .field("manufacturerCode").caption("Manufacturer Code")
                .field("collection").caption("Collection")
                .field("material").caption("Material")
                .field("colors").caption("Colors")
                .field("availability").caption("Availability")
                .field("gender").caption("Gender")
                .field("price").caption("Price")
                .field("published").caption("Published")
                .field("dimensionLength").caption("Length")
                .field("dimensionWidth").caption("Width")
                .field("dimensionHeight").caption("Height")
                .field("dimensionWeight").caption("Weight")
                .field("description").caption("Description")
                .field("productPhotos").caption("Photos")
                .field("productVideos").caption("Videos")
                .field("tags").caption("Tags")
                .build();
    }

    @Override
    public FieldSetConfigurationUnit formView(PersistentFieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("title").caption("Title")
                .field("brand").caption("Brand")
                .field("productCode").caption("Code")
                .field("manufacturer").caption("Manufacturer")
                .field("manufacturerCode").caption("Manufacturer Code")
                .field("collection").caption("Collection")
                .field("material").caption("Material")
                .field("colors").caption("Colors")
                .field("availability").caption("Availability").editor(enumeration("Available", "N/A"))
                .field("gender").caption("Gender").editor(enumeration("Unisex", "Male", "Female"))
                .field("price").caption("Price")
                .field("published").caption("Published")
                .field("dimensionLength").caption("Length")
                .field("dimensionWidth").caption("Width")
                .field("dimensionHeight").caption("Height")
                .field("dimensionWeight").caption("Weight")
                .field("description").caption("Description").editor(wysiwyg())
                .field("productPhotos").caption("Photos")
                .field("productVideos").caption("Videos")
                .field("tags").caption("Tags")
                .build();
    }

    @Override
    public FiltersConfigurationUnit filters(FiltersConfigurationUnitBuilder filterBuilder) {
        return filterBuilder
                .filter("Title", "title")
                .filter("Code", "productCode")
                .filter("Collection", "collection")
                .filter("Gender", "gender")
                .filter("Tags", "tags")
                .filter("Published", "published")
                .build();
    }

    @Override
    public ScopesConfigurationUnit scopes(ScopesConfigurationUnitBuilder scopeBuilder) {
        return scopeBuilder
                .scope("All", all()).defaultScope()
                .scope("Priced", specification(pricedProducts(true)))
                .scope("Not Priced", specification(pricedProducts(false)))
                .build();
    }

    private DomainTypeSpecification<Order> pricedProducts(final boolean processed) {
        return new DomainTypeSpecification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (processed) {
                    return cb.and(cb.isNotNull(root.get("price")), cb.greaterThan(root.<BigDecimal>get("price"), ZERO));
                }
                return cb.or(cb.isNull(root.get("price")), cb.equal(root.get("price"), ZERO)) ;
            }
        };
    }
}