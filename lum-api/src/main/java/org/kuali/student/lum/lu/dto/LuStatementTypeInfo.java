/*
 * Copyright 2008 The Kuali Foundation
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
package org.kuali.student.lum.lu.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.kuali.student.core.dto.TypeInfo;

@XmlAccessorType(XmlAccessType.FIELD)
public class LuStatementTypeInfo extends TypeInfo{

	private static final long serialVersionUID = 1L;

	private List<String> requiredComponentTypeIds;

    public List<String> getRequiredComponentTypeIds() {
        return requiredComponentTypeIds;
    }

    public void setRequiredComponentTypeIds(List<String> requiredComponentTypeIds) {
        this.requiredComponentTypeIds = requiredComponentTypeIds;
    }	
}