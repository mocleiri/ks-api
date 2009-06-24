/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kuali.student.lum.lo.entity;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;
import org.kuali.student.core.entity.RichText;

/**
 * @author Kuali Student Team
 */
@Entity
@Table(name = "KSLU_LO_HIRCHY")
public class LoHierarchy extends MetaEntity implements AttributeOwner<LoHierarchyAttribute> {
	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "NAME")
	String name;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "RT_DESCR_ID")
	private RichText desc;
	
	@OneToOne
	@JoinColumn(name = "LO_ROOT_ID")
	private Lo rootLo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFF_DT")
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIR_DT")
	private Date expirationDate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<LoHierarchyAttribute> attributes;

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(RichText desc) {
		this.desc = desc;
	}

	/**
	 * @return the desc
	 */
	public RichText getDesc() {
		return desc;
	}

	/**
	 * @param rootLo the rootLo to set
	 */
	public void setRootLo(Lo rootLo) {
		this.rootLo = rootLo;
	}

	/**
	 * @return the rootLo
	 */
	public Lo getRootLo() {
		return rootLo;
	}

	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @return the effectiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * @return the expirationDate
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.core.entity.AttributeOwner#getAttributes()
	 */
	@Override
	public List<LoHierarchyAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<LoHierarchyAttribute>(0);
		}
		return attributes;
	}

	/* (non-Javadoc)
	 * @see org.kuali.student.core.entity.AttributeOwner#setAttributes(java.util.List)
	 */
	@Override
	public void setAttributes(List<LoHierarchyAttribute> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
}