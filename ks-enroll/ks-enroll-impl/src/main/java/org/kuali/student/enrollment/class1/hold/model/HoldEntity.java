package org.kuali.student.enrollment.class1.hold.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.hold.dto.HoldInfo;
import org.kuali.student.r2.core.hold.infc.Hold;

@Entity
@Table(name = "KSEN_HOLD")
public class HoldEntity extends MetaEntity implements AttributeOwner<HoldAttributeEntity> {
    @Column(name = "NAME")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private HoldRichTextEntity descr;

    @Column(name = "TYPE_ID")
    private String holdType;

    @Column(name = "STATE_ID")
    private String holdState;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RELEASED_DT")
    private Date releasedDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ISSUE_ID")
    private IssueEntity issue;

    @Column(name = "PERS_ID")
    private String personId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<HoldAttributeEntity> attributes;

    @Override
    public void setAttributes(List<HoldAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    public HoldEntity() {}

    public HoldEntity(Hold hold) {
        super(hold);
        try {
            this.setId(hold.getId());
            this.setName(hold.getName());
            if (hold.getEffectiveDate() != null) {
                this.setEffectiveDate(hold.getEffectiveDate());
            }

            if (hold.getReleasedDate() != null) {
                this.setReleasedDate(hold.getReleasedDate());
            }

            this.setPersonId(hold.getPersonId());
            if (hold.getDescr() != null) {
                this.setDescr(new HoldRichTextEntity(hold.getDescr()));
            }

            if (hold.getStateKey() != null) {
                this.setHoldState(hold.getStateKey());
            }

            this.setAttributes(new ArrayList<HoldAttributeEntity>());

            if (null != hold.getAttributes()) {
                for (Attribute att : hold.getAttributes()) {
                    HoldAttributeEntity attEntity = new HoldAttributeEntity(att);
                    this.getAttributes().add(attEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HoldInfo toDto() {
        HoldInfo obj = new HoldInfo();
        obj.setId(getId());
        obj.setName(name);
        obj.setEffectiveDate(effectiveDate);
        obj.setReleasedDate(releasedDate);
        obj.setPersonId(personId);
        if (holdType != null) {
            obj.setTypeKey(holdType);
        }
        if (holdState != null) {
            obj.setStateKey(holdState);
        }
        if (issue != null) {
            obj.setIssueKey(issue.getId());
        }
        obj.setMeta(super.toDTO());
        if (descr != null) {
            obj.setDescr(descr.toDto());
        }

        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (HoldAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }

        obj.setAttributes(atts);
        return obj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HoldRichTextEntity getDescr() {
        return descr;
    }

    public void setDescr(HoldRichTextEntity descr) {
        this.descr = descr;
    }

    public String getHoldType() {
        return holdType;
    }

    public void setHoldType(String holdType) {
        this.holdType = holdType;
    }

    public String getHoldState() {
        return holdState;
    }

    public void setHoldState(String holdState) {
        this.holdState = holdState;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
    }

    public IssueEntity getIssue() {
        return issue;
    }

    public void setIssue(IssueEntity issue) {
        this.issue = issue;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public List<HoldAttributeEntity> getAttributes() {
        return attributes;
    }
}
