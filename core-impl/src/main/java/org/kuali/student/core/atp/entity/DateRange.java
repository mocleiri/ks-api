package org.kuali.student.core.atp.entity;

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
import org.kuali.student.core.entity.RichText;

@Entity
@Table(name = "KSAP_DT_RANGE")
public class DateRange extends MetaEntity implements AttributeOwner<DateRangeAttribute> {
	
	@Id
	@Column(name = "DATERANGE_KEY")
	private String id;

	@Column(name = "NAME")
	private String name;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "RT_DESCR_ID")
	private RichText desc;

	@ManyToOne
	@JoinColumn(name = "ATP_ID")
	private Atp atp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="START_DT")
	private Date startDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="END_DT")
	private Date endDate;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<DateRangeAttribute> attributes;

	@ManyToOne
	@JoinColumn(name = "DT_RANGE_TYPE_ID")
	private DateRangeType type;

	@Column(name="STATE")
	private String state;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RichText getDesc() {
		return desc;
	}

	public void setDesc(RichText desc) {
		this.desc = desc;
	}

	public Atp getAtp() {
		return atp;
	}

	public void setAtp(Atp atp) {
		this.atp = atp;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<DateRangeAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<DateRangeAttribute>();
		}
		return attributes;
	}

	public void setAttributes(List<DateRangeAttribute> attributes) {
		this.attributes = attributes;
	}

	public DateRangeType getType() {
		return type;
	}

	public void setType(DateRangeType type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
