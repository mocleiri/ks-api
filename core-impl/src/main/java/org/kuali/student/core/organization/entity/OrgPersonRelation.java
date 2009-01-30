package org.kuali.student.core.organization.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;

@Entity
@Table(name="KS_ORG_PERSON_REL_T")
public class OrgPersonRelation extends MetaEntity implements AttributeOwner<OrgPersonRelationAttribute>{
	@Id
	@Column(name = "ORG_PERSON_REL_ID")
	private String id; 
	
	@ManyToOne
    @JoinColumn(name="ORG")
	private Org org; 
	
	//Foreign Key from external Service
	@Column(name = "PERSON_ID")
	// @ManyToOne
    // @JoinColumn(name="PERSON_ID")
	private String personId; 
	
	@ManyToOne
    @JoinColumn(name = "ORG_PERSON_REL_TYPE")
	private OrgPersonRelationType orgPersonRelationType; 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFFECTIVE_DT")
	private Date effectiveDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIRATION_DT")
	private Date expirationDate; 
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<OrgPersonRelationAttribute> attributes;
	
	@Column(name = "ORG_PERSON_REL_STATE")
	private String state; 

	
	@Override
	public List<OrgPersonRelationAttribute> getAttributes() {
		if(attributes==null){
			attributes=new ArrayList<OrgPersonRelationAttribute>();
		}
		return attributes;
	}

	@Override
	public void setAttributes(List<OrgPersonRelationAttribute> attributes) {
		this.attributes = attributes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public OrgPersonRelationType getType() {
		return orgPersonRelationType;
	}

	public void setType(OrgPersonRelationType type) {
		this.orgPersonRelationType = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}



}
