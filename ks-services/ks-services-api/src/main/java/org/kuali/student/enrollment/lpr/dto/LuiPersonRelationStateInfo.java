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
package org.kuali.student.enrollment.lpr.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.kuali.student.common.dto.StateInfo;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationStateInfc;

/**
 * Unique identifier for the state of the relationship between a LUI and person.
 *
 * @Author Kamal
 * @Since Tue Mar 01 15:53:56 PST 2011
 * @See <a href="https://wiki.kuali.org/display/KULSTU/relationState+Structure">RelationState</a>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public final class LuiPersonRelationStateInfo extends StateInfo implements LuiPersonRelationStateInfc {
	
	private static final long serialVersionUID = 8428568906806883779L;
	
	private LuiPersonRelationStateInfo() {}
	
	private LuiPersonRelationStateInfo(LuiPersonRelationStateInfc builder) {
		super(builder);
	}

	public static class Builder extends StateInfo.Builder implements LuiPersonRelationStateInfc {
		public Builder() {}
		
		public Builder(LuiPersonRelationStateInfc lprStateInfo) {
			super(lprStateInfo);
		}
		public LuiPersonRelationStateInfc build() {
			return new LuiPersonRelationStateInfo(this);
		}
	}

}