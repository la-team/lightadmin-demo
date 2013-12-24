package org.lightadmin.demo.administration;

import org.lightadmin.demo.domain.Customer;
import org.lightadmin.demo.domain.Order;
import org.lightadmin.demo.persistence.OrderRepository;
import org.lightadmin.api.config.AdministrationConfiguration;
import org.lightadmin.api.config.builder.*;
import org.lightadmin.api.config.unit.EntityMetadataConfigurationUnit;
import org.lightadmin.api.config.unit.FieldSetConfigurationUnit;
import org.lightadmin.api.config.unit.FiltersConfigurationUnit;
import org.lightadmin.api.config.unit.ScopesConfigurationUnit;
import org.lightadmin.api.config.utils.DomainTypePredicate;
import org.lightadmin.api.config.utils.EntityNameExtractor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;
import static org.lightadmin.api.config.utils.Editors.textArea;
import static org.lightadmin.api.config.utils.ScopeMetadataUtils.all;
import static org.lightadmin.api.config.utils.ScopeMetadataUtils.filter;

@SuppressWarnings("unused")
public class CustomerAdministration extends AdministrationConfiguration<Customer> {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public EntityMetadataConfigurationUnit configuration(EntityMetadataConfigurationUnitBuilder configurationBuilder) {
        return configurationBuilder.pluralName("Customers").singularName("Customer").nameExtractor(customerNameExtractor()).build();
    }

    @Override
    public FieldSetConfigurationUnit listView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("name").caption("Name")
                .field("surname").caption("Surname")
                .field("phone").caption("Phone")
                .field("email").caption("Email")
                .build();
    }

    @Override
    public FieldSetConfigurationUnit quickView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("name").caption("Name")
                .field("surname").caption("Surname")
                .field("phone").caption("Phone")
                .field("email").caption("Email")
                .field("deliveryAddress").caption("Delivery Address")
                .build();
    }

    @Override
    public FieldSetConfigurationUnit showView(FieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("name").caption("Name")
                .field("surname").caption("Surname")
                .field("phone").caption("Phone")
                .field("email").caption("Email")
                .field("deliveryAddress").caption("Delivery Address")
                .build();
    }

    @Override
    public FieldSetConfigurationUnit formView(PersistentFieldSetConfigurationUnitBuilder fragmentBuilder) {
        return fragmentBuilder
                .field("name").caption("Name")
                .field("surname").caption("Surname")
                .field("phone").caption("Phone")
                .field("email").caption("Email")
                .field("deliveryAddress").caption("Delivery Address").editor(textArea())
                .build();
    }

    @Override
    public FiltersConfigurationUnit filters(FiltersConfigurationUnitBuilder filterBuilder) {
        return filterBuilder
                .filter("Name", "name")
                .filter("Surname", "surname")
                .filter("Phone", "phone")
                .build();
    }

    @Override
    public ScopesConfigurationUnit scopes(ScopesConfigurationUnitBuilder scopeBuilder) {
        return scopeBuilder.scope("All", all()).defaultScope()
                .scope(">$10", filter(topBuyerPredicate(10)))
                .scope(">$20", filter(topBuyerPredicate(20)))
                .scope(">$30", filter(topBuyerPredicate(30)))
                .build();
    }

    private DomainTypePredicate<Customer> topBuyerPredicate(final int ordersTotalSum) {
        return new DomainTypePredicate<Customer>() {
            @Override
            public boolean apply(Customer customer) {
                List<Order> orders = orderRepository.findByCustomer(customer);
                return totalOf(orders).compareTo(new BigDecimal(ordersTotalSum)) > 0;
            }
        };
    }

    private EntityNameExtractor<Customer> customerNameExtractor() {
        return new EntityNameExtractor<Customer>() {
            @Override
            public String apply(Customer customer) {
                if (isBlank(customer.getName()) && isBlank(customer.getSurname())) {
                    return customer.getEmail();
                }

                return format("%s %s", trimToEmpty(customer.getSurname()), trimToEmpty(customer.getName()));
            }
        };
    }

    private BigDecimal totalOf(List<Order> orders) {
        BigDecimal total = ZERO;
        for (Order order : orders) {
            total = total.add(order.getTotalPrice());
        }
        return total;
    }
}