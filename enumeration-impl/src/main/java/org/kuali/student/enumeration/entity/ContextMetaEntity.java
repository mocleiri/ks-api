package org.kuali.student.enumeration.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class ContextMetaEntity {
    @Id
    String id;
    String enumerationKey;
    String type;
    String dataType;
    int minLength;
    int maxLength;
    int minOccurs;
    int maxOccurs;
    /**
     * AutoGenerate the id
     */
    @PrePersist
    public void prePersist() {
        this.id = UUIDHelper.genStringUUID();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnumerationId() {
        return enumerationKey;
    }

    public void setEnumerationId(String enumerationId) {
        this.enumerationKey = enumerationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMinOccurs() {
        return minOccurs;
    }

    public void setMinOccurs(int minOccurs) {
        this.minOccurs = minOccurs;
    }

    public int getMaxOccurs() {
        return maxOccurs;
    }

    public void setMaxOccurs(int maxOccurs) {
        this.maxOccurs = maxOccurs;
    }

}
