package com.example.phonebook.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;

    AbstractBaseEntity(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("Entity %s (%s)", getClass().getName(), id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !getClass().equals(Hibernate.getClass(o))) return false;

        AbstractBaseEntity that = (AbstractBaseEntity) o;

        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id : 0;
    }

    public boolean isNew() {
        return getId() == null;
    }
}
