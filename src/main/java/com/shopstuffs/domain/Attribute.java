package com.shopstuffs.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Attribute.
 */
@Entity
@Table(name = "T_ATTRIBUTE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Attribute implements Serializable {

    @Id
    @Column(name = "attribute_id")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "attribute_name")
    private String name;
    @Column(name = "attribute_value")
    private String value;

    @Column(name = "attribute_type")
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String attributeName) {
        this.name = attributeName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String attributeValue) {
        this.value = attributeValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String attributeType) {
        this.type = attributeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Attribute attribute = (Attribute) o;

        if (!id.equals(attribute.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "id=" + id +
                ", attributeName='" + name + '\'' +
                ", attributeValue='" + value + '\'' +
                '}';
    }
}
