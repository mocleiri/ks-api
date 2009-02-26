package org.kuali.student.lum.lu.entity;

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
import javax.persistence.UniqueConstraint;

import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;
import org.kuali.student.core.entity.RichText;

@Entity
@Table(name = "KSLU_LU_DOC_RELTN", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"CLU_ID", "DOC_ID" }) })
public class LuDocumentRelation extends MetaEntity implements
		AttributeOwner<LuDocumentRelationAttribute> {
	@Id
	@Column(name = "ID")
	private String id;

	@ManyToOne
	@JoinColumn(name = "LU_DOC_RELTN_TYPE_ID")
	private LuDocumentRelationType luDocumentRelationType;

	@ManyToOne
	@JoinColumn(name = "CLU_ID")
	private Clu clu;

	@Column(name = "DOC_ID")
	private String documentId; // FOREIGN Service Relation

	@Column(name = "TITLE")
	private String title;

	@ManyToOne
	@JoinColumn(name = "DESCR")
	private RichText desc;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "OWNER")
	private List<LuDocumentRelationAttribute> attributes;

	@Column(name = "ST")
	private String state;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LuDocumentRelationType getLuDocumentRelationType() {
		return luDocumentRelationType;
	}

	public void setLuDocumentRelationType(
			LuDocumentRelationType luDocumentRelationType) {
		this.luDocumentRelationType = luDocumentRelationType;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public RichText getDesc() {
		return desc;
	}

	public void setDesc(RichText desc) {
		this.desc = desc;
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

	public List<LuDocumentRelationAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<LuDocumentRelationAttribute>();
		}
		return attributes;
	}

	public void setAttributes(List<LuDocumentRelationAttribute> attributes) {
		this.attributes = attributes;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setClu(Clu clu) {
		this.clu = clu;
	}

	public Clu getClu() {
		return clu;
	}

}
