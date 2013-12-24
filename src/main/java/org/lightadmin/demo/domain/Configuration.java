package org.lightadmin.demo.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "configurations")
public class Configuration extends AbstractEntity {

    @NotBlank
    @Column(name = "name")
    private String configurationKey;

    @Column(name = "value")
    private String configurationValue;

    public Configuration() {
    }

    public String getConfigurationKey() {
        return configurationKey;
    }

    public void setConfigurationKey(String configurationKey) {
        this.configurationKey = configurationKey;
    }

    public String getConfigurationValue() {
        return configurationValue;
    }

    public void setConfigurationValue(String configurationValue) {
        this.configurationValue = configurationValue;
    }
}