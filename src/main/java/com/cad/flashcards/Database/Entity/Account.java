package com.cad.flashcards.Database.Entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "firebase_uuid")
    private String uuid;

    @CreationTimestamp
    @Column(name="join_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "accountId")
    private Set<Data> data;

    public Account() {
    }

    public Account(String uuid, Date joinDate, Set<Data> data) {
        this.uuid = uuid;
        this.joinDate = joinDate;
        this.data = data;
    }

    public Account(String uuid) {
        this.uuid = uuid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Set<Data> getUserData() {
        return data;
    }

    public void setUserData(Set<Data> userData) {
        this.data = userData;
    }

    public void addUserData(Data userData) {
        userData.setAccountId(this.id);
        this.data.add(userData);
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account user = (Account) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (uuid != null ? !uuid.equals(user.uuid) : user.uuid != null) return false;
        if (joinDate != null ? !joinDate.equals(user.joinDate) : user.joinDate != null) return false;
        return data != null ? data.equals(user.data) : user.data == null;
    }

    @Override public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (joinDate != null ? joinDate.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override public String toString() {
        return "Account{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", joinDate=" + joinDate +
                ", data=" + data +
                '}';
    }
}
