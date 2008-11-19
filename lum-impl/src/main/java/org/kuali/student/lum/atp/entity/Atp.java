package org.kuali.student.lum.atp.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.core.entity.MetaEntity;

@Entity
public class Atp extends MetaEntity{
	@Id
	@Column(name = "ATP_KEY")
	private String key;
	private String name;
	@Column(name = "ATP_DESC")
	private String desc;
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationDate;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<AtpAttribute> attributes;
//	@Embedded
//	private Meta meta;
	@ManyToOne
	@JoinColumn(name="atpType")
	private AtpType type;
	private String state;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public List<AtpAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<AtpAttribute> attributes) {
		this.attributes = attributes;
	}

	public AtpType getType() {
		return type;
	}

	public void setType(AtpType type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

//	public Meta getMeta() {
//		return meta;
//	}
//
//	public void setMeta(Meta meta) {
//		this.meta = meta;
//	}

}
