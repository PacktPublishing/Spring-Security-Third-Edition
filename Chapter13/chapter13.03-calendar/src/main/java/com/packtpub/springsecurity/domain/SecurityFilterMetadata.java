package com.packtpub.springsecurity.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author Mick Knutson
 */
@Entity
@Table(name = "security_filtermetadata")
public class SecurityFilterMetadata implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String antPattern;
    private String expression;
    private Integer sortOrder;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getAntPattern() {
        return antPattern;
    }
    public void setAntPattern(String antPattern) {
        this.antPattern = antPattern;
    }

    public String getExpression() {
        return expression;
    }
    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

}
