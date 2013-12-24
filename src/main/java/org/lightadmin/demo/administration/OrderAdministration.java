package org.lightadmin.demo.administration;

import org.lightadmin.demo.domain.LineItem;
import org.lightadmin.demo.domain.Order;
import org.lightadmin.api.config.AdministrationConfiguration;
import org.lightadmin.api.config.builder.*;
import org.lightadmin.api.config.unit.EntityMetadataConfigurationUnit;
import org.lightadmin.api.config.unit.FieldSetConfigurationUnit;
import org.lightadmin.api.config.unit.FiltersConfigurationUnit;
import org.lightadmin.api.config.unit.ScopesConfigurationUnit;
import org.lightadmin.api.config.utils.DomainTypeSpecification;
import org.lightadmin.api.config.utils.EntityNameExtractor;
import org.lightadmin.api.config.utils.FieldValueRenderer;
import org.lightadmin.demo.domain.LineItem;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Set;

import static com.google.common.collect.Sets.newLinkedHashSet;
import static java.lang.String.format;
import static org.lightadmin.api.config.utils.ScopeMetadataUtils.all;
import static org.lightadmin.api.config.utils.ScopeMetadataUtils.specification;
import static org.springframework.util.StringUtils.collectionToDelimitedString;

@SuppressWarnings("unused")
public class OrderAdministration extends AdministrationConfiguration<Order> {

    @Override
    public EntityMetadataConfigurationUnit configuration(EntityMetadataConfigurationUnitBuilder configurationBuilder) {
        return configurationBuilder.pluralName("Orders").singularName("Order").nameExtractor(nameExtractor()).build();
    }

    @Override
    public FieldSetConfigurationUnit listView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("creationDate").caption("Date")
                .field("contactPerson").caption("Name")
                .field("contactPhone").caption("Phone")
                .dynamic("totalPrice").caption("Total Price")
                .field("processed").caption("Processed")
                .build();
    }

    @Override
    public FieldSetConfigurationUnit quickView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("contactPerson").caption("Name")
                .field("contactPhone").caption("Phone")
                .field("contactEmail").caption("Email")
                .field("deliveryAddress").caption("Delivery Address")
                .renderable(lineItemsFieldValueRenderer()).caption("Order Items")
                .field("processed").caption("Processed")
                .build();
    }

    @Override
    public FieldSetConfigurationUnit showView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("creationDate").caption("Date")
                .field("contactPerson").caption("Name")
                .field("contactPhone").caption("Phone")
                .field("contactEmail").caption("Email")
                .field("deliveryAddress").caption("Delivery Address")
                .renderable(lineItemsFieldValueRenderer()).caption("Order Items")
                .field("processed").caption("Processed")
                .field("comment").caption("Comment")
                .build();
    }

    @Override
    public FieldSetConfigurationUnit formView(PersistentFieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("creationDate").caption("Date")
                .field("contactPerson").caption("Name")
                .field("contactPhone").caption("Phone")
                .field("contactEmail").caption("Email")
                .field("deliveryAddress").caption("Delivery Address")
                .field("lineItems").caption("Items")
                .field("processed").caption("Processed")
                .field("comment").caption("Comment")
                .build();
    }

    @Override
    public FiltersConfigurationUnit filters(FiltersConfigurationUnitBuilder filterBuilder) {
        return filterBuilder
                .filter("ID", "id")
                .filter("Date", "creationDate")
                .filter("Name", "contactPerson")
                .filter("Phone", "contactPhone")
                .filter("Processed", "processed")
                .build();
    }

    @Override
    public ScopesConfigurationUnit scopes(ScopesConfigurationUnitBuilder scopeBuilder) {
        return scopeBuilder
                .scope("All", all()).defaultScope()
                .scope("Processed", specification(ordersByProcessingStatus(true)))
                .scope("Not processed", specification(ordersByProcessingStatus(false)))
                .build();
    }

    private DomainTypeSpecification<Order> ordersByProcessingStatus(final boolean processed) {
        return new DomainTypeSpecification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("processed"), processed);
            }
        };
    }

    private EntityNameExtractor<?> nameExtractor() {
        return new EntityNameExtractor<Order>() {
            @Override
            public String apply(Order order) {
                return format("Order #%s for %s", order.getId(), order.getTotalPrice());
            }
        };
    }

    private static FieldValueRenderer<Order> lineItemsFieldValueRenderer() {
        return new FieldValueRenderer<Order>() {
            @Override
            public String apply(final Order order) {
                Set<String> lineItems = newLinkedHashSet();
                for (LineItem lineItem : order.getLineItems()) {
                    lineItems.add(format("%s for %s", lineItem.getProduct().getTitle(), lineItem.getPrice()));
                }
                return collectionToDelimitedString(lineItems, "</br>");
            }
        };
    }
}