package org.kuali.student.core.organization.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;

@Entity
@Table(name="KS_ORG_HIERARCHY_ATTR_T")
public class OrgHierarchyAttribute extends Attribute<OrgHierarchy>{

	@ManyToOne
	@JoinColumn(name = "OWNER")
	private OrgHierarchy owner;
	
	@Override
	public OrgHierarchy getOwner() {
		return owner;
	}

	@Override
	public void setOwner(OrgHierarchy owner) {
		this.owner=owner;
	}
}
