package com.packtpub.springsecurity.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


/**
 *
 * @author Mick Knutson
 */
@Document(collection="role")
public class Role  implements Persistable<Integer>, Serializable {

    @Id
    private Integer id;
    private String name;

    public Role(){}

    @PersistenceConstructor
    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

//    private Set<CalendarUser> users;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

//    public Set<CalendarUser> getUsers() {
//        return users;
//    }
//    public void setUsers(Set<CalendarUser> users) {
//        this.users = users;
//    }


    private Boolean persisted = Boolean.FALSE;

    @Override
    public boolean isNew() {
        return !persisted;
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

} // The End...