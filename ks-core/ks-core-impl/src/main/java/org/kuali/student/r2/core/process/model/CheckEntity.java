package org.kuali.student.r2.core.process.model;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.process.dto.CheckInfo;
import org.kuali.student.r2.core.process.infc.Check;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "KSEN_CHECK")
public class CheckEntity extends MetaEntity implements AttributeOwner<CheckAttributeEntity> {

    //NAME
    @Column(name = "NAME")
    private String name;

    //RT_DESCR_ID
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private CheckRichTextEntity descr;

    //STATE_ID
    @Column(name = "CHECK_STATE")
    private String checkState;

    //TYPE_ID
    @Column(name = "CHECK_TYPE")
    private String checkType;

    @Column(name = "ISSUE_ID")
    private String issueId;

    @Column(name = "MILESTONE_TYPE_ID")
    private String milestoneTypeId;

    @ManyToOne(optional = true)
    @JoinColumn(name = "PROCESS_ID")
    private ProcessEntity process;

    @Column(name = "AGENDA_ID")
    private String agendaId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<CheckAttributeEntity> attributes;

	@Override
	public void setAttributes(List<CheckAttributeEntity> attributes) {
		this.attributes = attributes;
	}

    public CheckEntity() {}
    
	public CheckEntity(Check check){
	    super(check);
        this.setId(check.getKey());
        this.setName(check.getName());
        if(check.getDescr() != null) {
            this.setDescr(new CheckRichTextEntity(check.getDescr()));
        }
        if (check.getStateKey() != null) {
            this.setCheckState(check.getStateKey());
        }
        this.setAttributes(new ArrayList<CheckAttributeEntity>());
        if (null != check.getAttributes()) {
            for (Attribute att : check.getAttributes()) {
                CheckAttributeEntity attEntity = new CheckAttributeEntity(att);
                this.getAttributes().add(attEntity);
            }
        }

        this.setIssueId(check.getIssueId());
        this.setMilestoneTypeId(check.getMilestoneTypeKey());
        this.setAgendaId(check.getAgendaId());
	}

    /**
     * @return Process Information DTO
     */
    public CheckInfo toDto(){
        CheckInfo obj = new CheckInfo();
        obj.setMeta(super.toDTO());
        obj.setKey(getId());
        obj.setName(name);
        if (checkType != null) {
            obj.setTypeKey(checkType);
        }
        if (checkState != null) {
            obj.setStateKey(checkState);
        }
        if (descr != null) {
            obj.setDescr(descr.toDto());
        }

        if (null != process) {
            obj.setProcessKey(process.getId());
        }

        obj.setIssueId(issueId);
        obj.setMilestoneTypeKey(milestoneTypeId);
        obj.setAgendaId(agendaId);

        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (CheckAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        obj.setAttributes(atts);

        return obj;
	}

    // NAME
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // RT_DESCR_ID
    public CheckRichTextEntity getDescr() {
        return descr;
    }

    public void setDescr(CheckRichTextEntity descr) {
        this.descr = descr;
    }

	//PROCESS_ID
    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getMilestoneTypeId() {
        return milestoneTypeId;
    }

    public void setMilestoneTypeId(String milestoneTypeId) {
        this.milestoneTypeId = milestoneTypeId;
    }

    public ProcessEntity getProcess() {
        return process;
    }

    public void setProcess(ProcessEntity process) {
        this.process = process;
    }

    public String getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(String agendaId) {
        this.agendaId = agendaId;
    }

    @Override
	public List<CheckAttributeEntity> getAttributes() {
		 return attributes;
	}
}