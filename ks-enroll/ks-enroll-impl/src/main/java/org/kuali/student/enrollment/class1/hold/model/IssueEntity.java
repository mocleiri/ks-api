/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.hold.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.core.class1.state.model.StateEntity;
import org.kuali.student.r2.core.hold.dto.IssueInfo;
import org.kuali.student.r2.core.hold.infc.Issue;

/**
 * This is a description of what this class does - andy don't forget to fill this in. 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */

@Entity
@Table(name = "KSEN_ISSUE")
public class IssueEntity extends MetaEntity implements AttributeOwner<IssueAttributeEntity> {

    @Column(name = "NAME")
    private String name;
    
    @Column(name = "ORG_ID")
    private String organizationId;
    
    @Column(name = "TYPE_ID")
    private String issueType;
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "RT_DESCR_ID")
    private HoldRichTextEntity descr;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<IssueAttributeEntity> attributes;
    
    @Column(name = "STATE_ID")
    private String issueState;
    
    public IssueEntity() {
    }

    public IssueEntity(Issue issue) {
        super(issue);
        setName(issue.getName());
        setOrganizationId(issue.getOrganizationId());
        setIssueType(issue.getTypeKey());
      
        setIssueState(issue.getStateKey());

        setDescr(new HoldRichTextEntity(issue.getDescr()));
    }
    
    @Override
    public void setAttributes(List<IssueAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<IssueAttributeEntity> getAttributes() {
        return attributes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getIssueState() {
        return issueState;
    }

    public void setIssueState(String issueState) {
        this.issueState = issueState;
    }

    public HoldRichTextEntity getDescr() {
        return descr;
    }

    public void setDescr(HoldRichTextEntity descr) {
        this.descr = descr;
    }
    
    public IssueInfo toDto() {
        IssueInfo info = new IssueInfo();
        
        info.setKey(getId());
        info.setName(getName());
        info.setTypeKey(getIssueType());
        info.setStateKey(getIssueState());
        info.setOrganizationId(getOrganizationId());
        info.setMeta(super.toDTO());
        
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (IssueAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        info.setAttributes(atts);
        
        info.setDescr(getDescr().toDto());
        
        return info;
    }
    
}
