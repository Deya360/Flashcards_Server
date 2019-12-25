package com.cad.flashcards.Database.Entity;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "data")
public class Data {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "id")
    private Integer accountId;

    @CreationTimestamp
    @Column(name="backup_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date backupDate;

    @Column(name="categories", columnDefinition="TEXT")
    private String categories;

    @Column(name="words", columnDefinition="TEXT")
    private String words;

    @Column(name="results", columnDefinition="TEXT")
    private String results;


    public Data() {
    }

    public Data(Integer accountId, Date backupDate, String categories, String words, String results) {
        this.accountId = accountId;
        this.backupDate = backupDate;
        this.categories = categories;
        this.words = words;
        this.results = results;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Date getBackupDate() {
        return backupDate;
    }

    public void setBackupDate(Date backupDate) {
        this.backupDate = backupDate;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    /* This equals method takes into consideration only the data fields: categories, words and results, and the accountId */
    public boolean equalsPartial(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Data data = (Data) o;

        if (categories != null ? !categories.equals(data.categories) : data.categories != null) return false;
        if (words != null ? !words.equals(data.words) : data.words != null) return false;
        return results != null ? results.equals(data.results) : data.results == null;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Data data = (Data) o;

        if (id != null ? !id.equals(data.id) : data.id != null) return false;
        if (accountId != null ? !accountId.equals(data.accountId) : data.accountId != null) return false;
        if (backupDate != null ? !backupDate.equals(data.backupDate) : data.backupDate != null) return false;
        if (categories != null ? !categories.equals(data.categories) : data.categories != null) return false;
        if (words != null ? !words.equals(data.words) : data.words != null) return false;
        return results != null ? results.equals(data.results) : data.results == null;
    }

    @Override public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
        result = 31 * result + (backupDate != null ? backupDate.hashCode() : 0);
        result = 31 * result + (categories != null ? categories.hashCode() : 0);
        result = 31 * result + (words != null ? words.hashCode() : 0);
        result = 31 * result + (results != null ? results.hashCode() : 0);
        return result;
    }

    @Override public String toString() {
        return "Data{" +
                "id=" + id +
                ", accountId='" + accountId + '\'' +
                ", backupDate=" + backupDate +
                ", categories='" + categories + '\'' +
                ", words='" + words + '\'' +
                ", results='" + results + '\'' +
                '}';
    }

    public HashMap<String, String> pack() {
        HashMap<String, String> returnMap = new HashMap<>();
        returnMap.put("categories", categories);
        returnMap.put("words", words);
        returnMap.put("results", results);
        returnMap.put("backup_date", String.valueOf(backupDate.getTime()));
        return returnMap;
    }
}
