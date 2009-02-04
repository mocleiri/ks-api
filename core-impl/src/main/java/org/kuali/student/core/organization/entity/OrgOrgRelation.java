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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;

@Entity
@Table(name="KS_ORG_ORG_RELATION_T")
@NamedQueries({
			@NamedQuery(name = "OrgOrgRelation.getAllDescendants", query = "SELECT oor.relatedOrg.id FROM OrgOrgRelation oor "
				+ " WHERE oor.org.id = :orgId "
				+ "   AND oor.type.orgHierarchy.key = :orgHierarchy"),
			@NamedQuery(name="OrgOrgRelation.getOrgOrgRelationsByIdList", query="SELECT oor FROM OrgOrgRelation oor WHERE oor.id IN (:idList)"),
			@NamedQuery(name="OrgOrgRelation.OrgOrgRelation", query="SELECT oor FROM OrgOrgRelation oor WHERE oor.org.id = :orgId"),
			@NamedQuery(name="OrgOrgRelation.getOrgOrgRelationsByRelatedOrg", query="SELECT oor FROM OrgOrgRelation oor WHERE oor.relatedOrg.id = :relatedOrgId")
})
public class OrgOrgRelation extends MetaEntity implements
		AttributeOwner<OrgOrgRelationAttribute> {
	@Id
	@Column(name = "ORG_KEY")
	private String id;

	@ManyToOne
    @JoinColumn(name="ORG")
	private Org org;

	@ManyToOne
    @JoinColumn(name="RELATED_ORG")
    private Org relatedOrg;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFFECTIVE_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<OrgOrgRelationAttribute> attributes;

	@ManyToOne
    @JoinColumn(name="RELATION_TYPE")
	private OrgOrgRelationType type;

	@Column(name = "RELATION_STATE")
	private String state;

	/**
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
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

	public Org getRelatedOrg() {
		return relatedOrg;
	}

	public void setRelatedOrg(Org relatedOrg) {
		this.relatedOrg = relatedOrg;
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

	@Override
	public List<OrgOrgRelationAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<OrgOrgRelationAttribute>();
		}
		return attributes;
	}

	@Override
	public void setAttributes(List<OrgOrgRelationAttribute> attributes) {
		this.attributes = attributes;
	}

	public OrgOrgRelationType getType() {
		return type;
	}

	public void setType(OrgOrgRelationType type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
