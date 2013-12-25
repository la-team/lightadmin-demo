package org.lightadmin.demo.administration;

import org.lightadmin.api.config.AdministrationConfiguration;
import org.lightadmin.api.config.builder.EntityMetadataConfigurationUnitBuilder;
import org.lightadmin.api.config.builder.FieldSetConfigurationUnitBuilder;
import org.lightadmin.api.config.builder.PersistentFieldSetConfigurationUnitBuilder;
import org.lightadmin.api.config.unit.EntityMetadataConfigurationUnit;
import org.lightadmin.api.config.unit.FieldSetConfigurationUnit;
import org.lightadmin.demo.domain.Page;

import static org.lightadmin.api.config.utils.Editors.wysiwyg;

@SuppressWarnings("unused")
public class PageAdministration extends AdministrationConfiguration<Page> {

    @Override
    public EntityMetadataConfigurationUnit configuration(EntityMetadataConfigurationUnitBuilder configurationBuilder) {
        return configurationBuilder.pluralName("Pages").singularName("Page").nameField("title").build();
    }

    @Override
    public FieldSetConfigurationUnit listView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("orderIdx").caption("Order")
                .field("name").caption("Name")
                .field("title").caption("Title")
                .field("published").caption("Published")
                .build();
    }

    @Override
    public FieldSetConfigurationUnit quickView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("orderIdx").caption("Order")
                .field("name").caption("Name")
                .field("title").caption("Title")
                .field("published").caption("Published")
                .build();
    }

    @Override
    public FieldSetConfigurationUnit showView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("orderIdx").caption("Order")
                .field("name").caption("Name")
                .field("title").caption("Title")
                .field("published").caption("Published")
                .field("content").caption("Content")
                .build();
    }

    @Override
    public FieldSetConfigurationUnit formView(PersistentFieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("orderIdx").caption("Order")
                .field("name").caption("Name")
                .field("title").caption("Title")
                .field("published").caption("Published")
                .field("content").caption("Content").editor(wysiwyg())
                .build();
    }
}