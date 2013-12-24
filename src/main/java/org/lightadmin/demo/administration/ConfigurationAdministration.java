package org.lightadmin.demo.administration;

import org.lightadmin.demo.domain.Configuration;
import org.lightadmin.api.config.AdministrationConfiguration;
import org.lightadmin.api.config.builder.*;
import org.lightadmin.api.config.unit.EntityMetadataConfigurationUnit;
import org.lightadmin.api.config.unit.FieldSetConfigurationUnit;
import org.lightadmin.api.config.unit.FiltersConfigurationUnit;
import org.lightadmin.api.config.unit.ScreenContextConfigurationUnit;
import org.lightadmin.api.config.utils.EntityNameExtractor;
import org.lightadmin.demo.domain.Configuration;

import static java.lang.String.format;
import static org.lightadmin.api.config.utils.Editors.textArea;

@SuppressWarnings("unused")
public class ConfigurationAdministration extends AdministrationConfiguration<Configuration> {

    @Override
    public EntityMetadataConfigurationUnit configuration(EntityMetadataConfigurationUnitBuilder configurationBuilder) {
        return configurationBuilder.pluralName("Configurations").singularName("Configuration").nameExtractor(nameExtractor()).build();
    }

    @Override
    public ScreenContextConfigurationUnit screenContext(ScreenContextConfigurationUnitBuilder screenContextBuilder) {
        return screenContextBuilder.screenName("Configuration administration").build();
    }

    @Override
    public FieldSetConfigurationUnit listView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("configurationKey").caption("Name")
                .field("configurationValue").caption("Value").build();
    }

    @Override
    public FieldSetConfigurationUnit quickView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("configurationKey").caption("Name")
                .field("configurationValue").caption("Value").build();
    }

    @Override
    public FieldSetConfigurationUnit showView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("configurationKey").caption("Name")
                .field("configurationValue").caption("Value").build();
    }

    @Override
    public FieldSetConfigurationUnit formView(PersistentFieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("configurationKey").caption("Name")
                .field("configurationValue").caption("Value").editor(textArea()).build();
    }

    @Override
    public FiltersConfigurationUnit filters(FiltersConfigurationUnitBuilder filterBuilder) {
        return filterBuilder.filter("Name", "configurationKey").build();
    }

    private EntityNameExtractor<?> nameExtractor() {
        return new EntityNameExtractor<Configuration>() {
            @Override
            public String apply(Configuration configuration) {
                return format("Configuration '%s'", configuration.getConfigurationKey());
            }
        };
    }
}