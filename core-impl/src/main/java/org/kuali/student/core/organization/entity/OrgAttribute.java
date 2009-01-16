package org.kuali.student.core.organization.entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.kuali.student.core.entity.Attribute;

public class OrgAttribute extends Attribute<Org, OrgAttributeDef>{

	@ManyToOne
	@JoinColumn(name = "OWNER")
	private Org owner;
	
	@Override
	public Org getOwner() {
		return owner;
	}

	@Override
	public void setOwner(Org owner) {
		this.owner=owner;
	}

}
