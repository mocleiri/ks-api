package org.kuali.student.core.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FieldDescriptorEntity {
    
    @Column(name = "NAME", nullable = false)
    protected String name;
    @Column(name = "DESCR", nullable = false)
    protected String desc;
    @Column(name = "DATA_TYPE", nullable = false)
    protected String dataType;
    @Column(name="MIN_VALUE")
    protected String minValue;
    @Column(name="MAX_VALUE")
    protected String maxValue;
    @Column(name="MIN_LENGTH")
    protected Integer minLength;
    @Column(name="MAX_LENGTH")
    protected Integer maxLength;
    @Column(name="VALID_CHARS")
    protected String validChars;
    @Column(name="INVALID_CHARS")
    protected String invalidChars;
    @Column(name="MIN_OCCURS")
    protected Integer minOccurs;
    @Column(name="MAX_OCCURS")
    protected Integer maxOccurs;
    @Column(name="READ_ONLY", nullable = false)
    protected boolean readOnly = false;
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }
    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
    /**
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }
    /**
     * @param dataType the dataType to set
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    /**
     * @return the minValue
     */
    public String getMinValue() {
        return minValue;
    }
    /**
     * @param minValue the minValue to set
     */
    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }
    /**
     * @return the maxValue
     */
    public String getMaxValue() {
        return maxValue;
    }
    /**
     * @param maxValue the maxValue to set
     */
    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }
    /**
     * @return the minLength
     */
    public Integer getMinLength() {
        return minLength;
    }
    /**
     * @param minLength the minLength to set
     */
    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }
    /**
     * @return the maxLength
     */
    public Integer getMaxLength() {
        return maxLength;
    }
    /**
     * @param maxLength the maxLength to set
     */
    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }
    /**
     * @return the validChars
     */
    public String getValidChars() {
        return validChars;
    }
    /**
     * @param validChars the validChars to set
     */
    public void setValidChars(String validChars) {
        this.validChars = validChars;
    }
    /**
     * @return the invalidChars
     */
    public String getInvalidChars() {
        return invalidChars;
    }
    /**
     * @param invalidChars the invalidChars to set
     */
    public void setInvalidChars(String invalidChars) {
        this.invalidChars = invalidChars;
    }
    /**
     * @return the minOccurs
     */
    public Integer getMinOccurs() {
        return minOccurs;
    }
    /**
     * @param minOccurs the minOccurs to set
     */
    public void setMinOccurs(Integer minOccurs) {
        this.minOccurs = minOccurs;
    }
    /**
     * @return the maxOccurs
     */
    public Integer getMaxOccurs() {
        return maxOccurs;
    }
    /**
     * @param maxOccurs the maxOccurs to set
     */
    public void setMaxOccurs(Integer maxOccurs) {
        this.maxOccurs = maxOccurs;
    }
    /**
     * @return the readOnly
     */
    public boolean isReadOnly() {
        return readOnly;
    }
    /**
     * @param readOnly the readOnly to set
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }    
}
