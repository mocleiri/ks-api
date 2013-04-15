/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.lum.lo.entity;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.r1.common.entity.Type;

@Entity
@Table(name = "KSLO_LO_TYPE")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="ID")),
    @AttributeOverride(name="descr", column=@Column(name="DESCR"))})
public class LoType extends Type<LoTypeAttribute> {
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	private List<LoTypeAttribute> attributes;

	@Override
	public List<LoTypeAttribute> getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(List<LoTypeAttribute> attributes) {
		this.attributes = attributes;
	}

}
