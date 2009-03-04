package org.kuali.student.core.organization.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.core.entity.AttributeOwner;

@Entity
@Table(name="KSOR_ORG_HIRCHY")
public class OrgHierarchy implements AttributeOwner<OrgHierarchyAttribute>{
	
	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "NAME")
	private String name; 
	
	@Column(name = "DESCR",length=2000)//TODO what is a good number for these long descriptions?
	private String desc; 

	@ManyToOne
    @JoinColumn(name="ROOT_ORG")
	private Org rootOrg; 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;  

	@ManyToMany
	@JoinTable(
	        name="KSOR_ORG_HIRCHY_JN_ORG_TYPE",
	        joinColumns=
	            @JoinColumn(name="ORG_HIRCHY_ID", referencedColumnName="ID"),
	        inverseJoinColumns=
	            @JoinColumn(name="ORG_TYPE_ID", referencedColumnName="TYPE_KEY")
	    )
	private List<OrgType> organizationTypes;
	
	@OneToMany(mappedBy = "orgHierarchy")
	private List<OrgOrgRelationType> orgOrgRelationTypes;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="OWNER")
	private List<OrgHierarchyAttribute> attributes;
	
	@Override
	public List<OrgHierarchyAttribute> getAttributes() {
		if(attributes==null){
			attributes=new ArrayList<OrgHierarchyAttribute>();
		}
		return attributes;
	}

	@Override
	public void setAttributes(List<OrgHierarchyAttribute> attributes) {
		this.attributes=attributes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Org getRootOrg() {
		return rootOrg;
	}

	public void setRootOrg(Org rootOrg) {
		this.rootOrg = rootOrg;
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

	public List<OrgType> getOrganizationTypes() {
		if(organizationTypes==null){
			organizationTypes=new ArrayList<OrgType>();
		}
		return organizationTypes;
	}

	public void setOrganizationTypes(List<OrgType> organizationTypes) {
		this.organizationTypes = organizationTypes;
	}

	public List<OrgOrgRelationType> getOrgOrgRelationTypes() {
		if(orgOrgRelationTypes==null){
			orgOrgRelationTypes=new ArrayList<OrgOrgRelationType>();
		}
		return orgOrgRelationTypes;
	}

	public void setOrgOrgRelationTypes(List<OrgOrgRelationType> orgOrgRelationTypes) {
		this.orgOrgRelationTypes = orgOrgRelationTypes;
	}

}
