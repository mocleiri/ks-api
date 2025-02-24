/*
 * Copyright 2014 The Kuali Foundation Licensed under the
 *  Educational Community License, Version 2.0 (the "License"); you may
 *  not use this file except in compliance with the License. You may
 *  obtain a copy of the License at
 *
 *   http://www.osedu.org/licenses/ECL-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS"
 *  BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package org.kuali.student.core.ges.dto;


import org.kuali.student.core.ges.infc.CopyableSerializable;
import org.kuali.student.core.ges.infc.GesCustomValue;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValueInfo", propOrder = {
         "value","typeKey","_futureElements" })
public class GesCustomValueInfo implements GesCustomValue {
    @XmlAnyElement
    private String typeKey;
    @XmlAnyElement
    private CopyableSerializable value;

    @XmlAnyElement
    private List<Object> _futureElements;


    public GesCustomValueInfo() {
    }

    public GesCustomValueInfo(GesCustomValue gesCustomValue) {

        if(gesCustomValue != null) {
            if(gesCustomValue.getValue() != null) {
                value = gesCustomValue.getValue().copy();
            }
        }
    }

    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    @Override
    public CopyableSerializable getValue() {
        return value;
    }

    public void setValue(CopyableSerializable value) {
        this.value = value;
    }
}
