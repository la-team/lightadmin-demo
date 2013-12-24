package org.lightadmin.demo.administration;

import org.lightadmin.demo.domain.ProductPhoto;
import org.lightadmin.api.config.AdministrationConfiguration;
import org.lightadmin.api.config.builder.*;
import org.lightadmin.api.config.unit.EntityMetadataConfigurationUnit;
import org.lightadmin.api.config.unit.FieldSetConfigurationUnit;
import org.lightadmin.api.config.unit.FiltersConfigurationUnit;
import org.lightadmin.api.config.unit.ScopesConfigurationUnit;
import org.lightadmin.api.config.utils.DomainTypeSpecification;
import org.lightadmin.api.config.utils.EntityNameExtractor;
import org.lightadmin.demo.domain.ProductPhoto;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static java.lang.String.format;
import static org.lightadmin.api.config.utils.ScopeMetadataUtils.all;
import static org.lightadmin.api.config.utils.ScopeMetadataUtils.specification;

@SuppressWarnings("unused")
public class ProductPhotoAdministration extends AdministrationConfiguration<ProductPhoto> {

    @Override
    public EntityMetadataConfigurationUnit configuration(EntityMetadataConfigurationUnitBuilder configurationBuilder) {
        return configurationBuilder.pluralName("Photos").singularName("Photo").nameExtractor(nameExtractor()).build();
    }

    @Override
    public FieldSetConfigurationUnit listView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return commonFieldSet(fragmentBuilder).build();
    }

    @Override
    public FieldSetConfigurationUnit quickView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return commonFieldSet(fragmentBuilder)
                .dynamic("image").caption("Image File Path")
                .build();
    }

    @Override
    public FieldSetConfigurationUnit showView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return commonFieldSet(fragmentBuilder)
                .dynamic("image").caption("Image File Path")
                .build();
    }

    @Override
    public FieldSetConfigurationUnit formView(PersistentFieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("product").caption("Product")
                .field("image").caption("Image")
                .field("main").caption("Main Photo")
                .build();
    }

    private FieldSetConfigurationUnitBuilder commonFieldSet(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("product").caption("Product")
                .field("image").caption("Image")
                .field("main").caption("Main Photo");
    }

    @Override
    public FiltersConfigurationUnit filters(FiltersConfigurationUnitBuilder filterBuilder) {
        return filterBuilder
                .filter("Product", "product")
                .filter("Main Photo", "main")
                .build();
    }

    @Override
    public ScopesConfigurationUnit scopes(ScopesConfigurationUnitBuilder scopeBuilder) {
        return scopeBuilder
                .scope("All", all()).defaultScope()
                .scope("Main photos only", specification(mainPhotos()))
                .build();
    }

    private DomainTypeSpecification<ProductPhoto> mainPhotos() {
        return new DomainTypeSpecification<ProductPhoto>() {
            @Override
            public Predicate toPredicate(Root<ProductPhoto> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("main"), true) ;
            }
        };
    }

    private EntityNameExtractor<?> nameExtractor() {
        return new EntityNameExtractor<ProductPhoto>() {
            @Override
            public String apply(ProductPhoto productPhoto) {
                return format("%s: photo #%s", productPhoto.getProduct().getTitle(), productPhoto.getId());
            }
        };
    }
}