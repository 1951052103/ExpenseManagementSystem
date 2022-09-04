/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "custom_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomGroup.findAll", query = "SELECT c FROM CustomGroup c"),
    @NamedQuery(name = "CustomGroup.findById", query = "SELECT c FROM CustomGroup c WHERE c.id = :id"),
    @NamedQuery(name = "CustomGroup.findByName", query = "SELECT c FROM CustomGroup c WHERE c.name = :name"),
    @NamedQuery(name = "CustomGroup.findByActive", query = "SELECT c FROM CustomGroup c WHERE c.active = :active")})
public class CustomGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "{group.invalidName}")
    private String name;
    @Column(name = "active")
    private Boolean active;
    @OneToMany(mappedBy = "groupId")
    @JsonIgnore
    private Set<Income> incomeSet;
    @OneToMany(mappedBy = "groupId")
    @JsonIgnore
    private Set<GroupUser> groupUserSet;
    @OneToMany(mappedBy = "groupId")
    @JsonIgnore
    private Set<Expense> expenseSet;

    public CustomGroup() {
    }

    public CustomGroup(Integer id) {
        this.id = id;
    }

    public CustomGroup(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @XmlTransient
    public Set<Income> getIncomeSet() {
        return incomeSet;
    }

    public void setIncomeSet(Set<Income> incomeSet) {
        this.incomeSet = incomeSet;
    }

    @XmlTransient
    public Set<GroupUser> getGroupUserSet() {
        return groupUserSet;
    }

    public void setGroupUserSet(Set<GroupUser> groupUserSet) {
        this.groupUserSet = groupUserSet;
    }

    @XmlTransient
    public Set<Expense> getExpenseSet() {
        return expenseSet;
    }

    public void setExpenseSet(Set<Expense> expenseSet) {
        this.expenseSet = expenseSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomGroup)) {
            return false;
        }
        CustomGroup other = (CustomGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.btl.pojo.CustomGroup[ id=" + id + " ]";
    }
    
}
