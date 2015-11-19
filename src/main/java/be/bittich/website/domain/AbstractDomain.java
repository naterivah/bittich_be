package be.bittich.website.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Nordine on 17-10-15.
 */
@MappedSuperclass
public abstract class AbstractDomain implements Serializable {

    @JsonIgnore
    private Timestamp createdDate;

    @JsonIgnore
    private Timestamp updatedDate;

    @JsonIgnore
    private Long version;

    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Column(insertable = true, updatable = false)
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Column(insertable = false, updatable = true)
    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    @PrePersist
    void onCreate() {
        this.setCreatedDate(new Timestamp((new Date()).getTime()));
    }

    @PreUpdate
    void onPersist() {
        this.setUpdatedDate(new Timestamp((new Date()).getTime()));
    }


}