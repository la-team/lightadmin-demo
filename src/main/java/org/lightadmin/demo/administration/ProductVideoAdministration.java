package org.lightadmin.demo.administration;

import org.lightadmin.demo.domain.ProductVideo;
import org.lightadmin.api.config.AdministrationConfiguration;
import org.lightadmin.api.config.builder.EntityMetadataConfigurationUnitBuilder;
import org.lightadmin.api.config.builder.FieldSetConfigurationUnitBuilder;
import org.lightadmin.api.config.builder.FiltersConfigurationUnitBuilder;
import org.lightadmin.api.config.builder.PersistentFieldSetConfigurationUnitBuilder;
import org.lightadmin.api.config.unit.EntityMetadataConfigurationUnit;
import org.lightadmin.api.config.unit.FieldSetConfigurationUnit;
import org.lightadmin.api.config.unit.FiltersConfigurationUnit;
import org.lightadmin.api.config.utils.EntityNameExtractor;
import org.lightadmin.demo.domain.ProductVideo;

import static java.lang.String.format;

@SuppressWarnings("unused")
public class ProductVideoAdministration extends AdministrationConfiguration<ProductVideo> {

    @Override
    public EntityMetadataConfigurationUnit configuration(EntityMetadataConfigurationUnitBuilder configurationBuilder) {
        return configurationBuilder.pluralName("Videos").singularName("Video").nameExtractor(nameExtractor()).build();
    }

    @Override
    public FieldSetConfigurationUnit listView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return commonFieldSet(fragmentBuilder).build();
    }

    @Override
    public FieldSetConfigurationUnit quickView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return commonFieldSet(fragmentBuilder).build();
    }

    @Override
    public FieldSetConfigurationUnit showView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return commonFieldSet(fragmentBuilder).build();
    }

    @Override
    public FieldSetConfigurationUnit formView(PersistentFieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("product").caption("Product")
                .field("videoUrl").caption("Video Url")
                .build();
    }

    private FieldSetConfigurationUnitBuilder commonFieldSet(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("product").caption("Product")
                .field("videoUrl").caption("Video Url");
    }

    @Override
    public FiltersConfigurationUnit filters(FiltersConfigurationUnitBuilder filterBuilder) {
        return filterBuilder.filter("Product", "product").build();
    }

    private EntityNameExtractor<?> nameExtractor() {
        return new EntityNameExtractor<ProductVideo>() {
            @Override
            public String apply(ProductVideo productVideo) {
                return format("%s: video #%s", productVideo.getProduct().getTitle(), productVideo.getId());
            }
        };
    }
}