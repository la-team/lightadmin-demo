package org.lightadmin.demo.administration;

import org.lightadmin.demo.domain.CallbackRequest;
import org.joda.time.DateTime;
import org.lightadmin.api.config.AdministrationConfiguration;
import org.lightadmin.api.config.builder.*;
import org.lightadmin.api.config.unit.EntityMetadataConfigurationUnit;
import org.lightadmin.api.config.unit.FieldSetConfigurationUnit;
import org.lightadmin.api.config.unit.FiltersConfigurationUnit;
import org.lightadmin.api.config.unit.ScopesConfigurationUnit;
import org.lightadmin.api.config.utils.DomainTypePredicate;

import static org.joda.time.DateTime.now;
import static org.joda.time.Days.daysBetween;
import static org.lightadmin.api.config.utils.Editors.textArea;
import static org.lightadmin.api.config.utils.ScopeMetadataUtils.all;
import static org.lightadmin.api.config.utils.ScopeMetadataUtils.filter;

@SuppressWarnings("unused")
public class CallbackRequestAdministration extends AdministrationConfiguration<CallbackRequest> {

    @Override
    public EntityMetadataConfigurationUnit configuration(EntityMetadataConfigurationUnitBuilder configurationBuilder) {
        return configurationBuilder.pluralName("Callback Requests").singularName("Callback Request").build();
    }

    @Override
    public FieldSetConfigurationUnit listView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return commonFieldSet(fragmentBuilder)
                .field("processed").caption("Processed")
                .build();
    }

    @Override
    public FieldSetConfigurationUnit quickView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return commonFieldSet(fragmentBuilder)
                .field("contactEmail").caption("Email")
                .field("comment").caption("Comment")
                .field("processed").caption("Processed")
                .build();
    }

    @Override
    public FieldSetConfigurationUnit showView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return commonFieldSet(fragmentBuilder)
                .field("contactEmail").caption("Email")
                .field("comment").caption("Comment")
                .field("processed").caption("Processed")
                .build();
    }

    @Override
    public FieldSetConfigurationUnit formView(PersistentFieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("creationDate").caption("Date")
                .field("product").caption("Product")
                .field("contactPerson").caption("Name")
                .field("contactPhone").caption("Phone")
                .field("contactEmail").caption("Email")
                .field("comment").caption("Comment").editor(textArea())
                .field("processed").caption("Processed")
                .build();
    }

    @Override
    public FiltersConfigurationUnit filters(FiltersConfigurationUnitBuilder filterBuilder) {
        return filterBuilder
                .filter("Product", "product")
                .filter("Processed", "processed")
                .build();
    }

    @Override
    public ScopesConfigurationUnit scopes(ScopesConfigurationUnitBuilder scopeBuilder) {
        return scopeBuilder.scope("All", all()).defaultScope()
                .scope("Last 3 days", filter(requestsForLastNDays(3)))
                .scope("Last day", filter(requestsForLastNDays(1)))
                .build();
    }

    private DomainTypePredicate<CallbackRequest> requestsForLastNDays(final int days) {
        final DateTime today = now();
        return new DomainTypePredicate<CallbackRequest>() {
            @Override
            public boolean apply(CallbackRequest callbackRequest) {
                final DateTime callbackRequestCreationDate = new DateTime(callbackRequest.getCreationDate());

                return daysBetween(callbackRequestCreationDate, today).getDays() <= days;
            }
        };
    }

    private FieldSetConfigurationUnitBuilder commonFieldSet(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("creationDate").caption("Date")
                .field("product").caption("Product")
                .field("contactPerson").caption("Name")
                .field("contactPhone").caption("Phone");
    }
}