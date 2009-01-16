/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.rules.rulemanagement.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.poc.common.ws.binding.JaxbAttributeMapListAdapter;

/**
 * Contains meta data about a functional business rule. Since a functional business rule is composed of one or more Rule
 * Elements, this class is associated with one or more RuleElementDTO instances. The class also contains RuleMetaDataDTO instance.
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BusinessRuleInfoDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private String id;

    @XmlElement
    private String originalRuleId;
        
    @XmlElement
    private String type;
    
    @XmlElement
    private String anchorValue;
    
    @XmlElement
    private String anchorTypeKey;
        
    @XmlElement
    private String name;
    
    @XmlElement
    private String desc;

    @XmlElement    
    private String successMessage;
    
    @XmlElement
    private String failureMessage;

    @XmlElement
    private Date effectiveDate;
    
    @XmlElement
    private Date expirationDate;
    
    @XmlElement
    private String state;
        
    @XmlElement
    private String compiledId;

    @XmlElement
    private String repositorySnapshotName;
    
    @XmlElement(name = "businessRuleElement")
    @XmlElementWrapper(name = "businessRuleElementList")
    private List<RuleElementDTO> businessRuleElementList = new ArrayList<RuleElementDTO>();
    
    @XmlElement(name="attribute")
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> attributeList;

    
    @XmlElement
    private MetaInfoDTO metaInfo;


    /**
     * @return the id
     */
    public String getId() {
        return id;
    }


    /**
     * @param id the id to set
     */
    public void setId(String businessRuleId) {
        this.id = businessRuleId;
    }


    /**
     * @return the type
     */
    public String getType() {
        return type;
    }


    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }


    /**
     * @return the anchorValue
     */
    public String getAnchorValue() {
        return anchorValue;
    }


    /**
     * @param anchorValue the anchorValue to set
     */
    public void setAnchorValue(String anchorValue) {
        this.anchorValue = anchorValue;
    }


    /**
     * @return the anchorTypeKey
     */
    public String getAnchorTypeKey() {
        return anchorTypeKey;
    }


    /**
     * @param anchorTypeKey the anchorTypeKey to set
     */
    public void setAnchorTypeKey(String anchorTypeKey) {
        this.anchorTypeKey = anchorTypeKey;
    }

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
     * @return the successMessage
     */
    public String getSuccessMessage() {
        return successMessage;
    }


    /**
     * @param successMessage the successMessage to set
     */
    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }


    /**
     * @return the failureMessage
     */
    public String getFailureMessage() {
        return failureMessage;
    }


    /**
     * @param failureMessage the failureMessage to set
     */
    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }


    /**
     * @return the effectiveDate
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }


    /**
     * @param effectiveDate the effectiveDate to set
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }


    /**
     * @return the expirationDate
     */
    public Date getExpirationDate() {
        return expirationDate;
    }


    /**
     * @param expirationDate the expirationDate to set
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }


    /**
     * @return the state
     */
    public String getState() {
        return state;
    }


    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the businessRuleElementList
     */
    public List<RuleElementDTO> getBusinessRuleElementList() {
        return businessRuleElementList;
    }


    /**
     * @param businessRuleElementList the businessRuleElementList to set
     */
    public void setBusinessRuleElementList(List<RuleElementDTO> businessRuleElementList) {
        this.businessRuleElementList = businessRuleElementList;
    }


    /**
     * @return the metaInfo
     */
    public MetaInfoDTO getMetaInfo() {
        return metaInfo;
    }


    /**
     * @param metaInfo the metaInfo to set
     */
    public void setMetaInfo(MetaInfoDTO metaInfo) {
        this.metaInfo = metaInfo;
    }


    /**
     * @return the compiledId
     */
    public String getCompiledId() {
        return compiledId;
    }


    /**
     * @param compiledId the compiledId to set
     */
    public void setCompiledId(String compiledId) {
        this.compiledId = compiledId;
    }


    /**
     * @return the repositorySnapshotName
     */
    public String getRepositorySnapshotName() {
        return repositorySnapshotName;
    }


    /**
     * @param repositorySnapshotName the repositorySnapshotName to set
     */
    public void setRepositorySnapshotName(String repositorySnapshotName) {
        this.repositorySnapshotName = repositorySnapshotName;
    }


    /**
     * @return the originalRuleId
     */
    public String getOriginalRuleId() {
        return originalRuleId;
    }

    /**
     * @param originalRuleId the originalRuleId to set
     */
    public void setOriginalRuleId(String originalRuleId) {
        this.originalRuleId = originalRuleId;
    }

    /**
     * @return the attributeList
     */
    public Map<String, String> getAttributeList() {
        return attributeList;
    }


    /**
     * @param attributeList the attributeList to set
     */
    public void setAttributeList(Map<String, String> attributeList) {
        this.attributeList = attributeList;
    }        
}
