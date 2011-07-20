/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.enrollment.lpr.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.lpr.infc.RequestOption;
import org.w3c.dom.Element;

/**
 * This is a description of what this class does - sambitpatnaik don't forget to
 * fill this in.
 * 
 * @author Kuali Student Team (sambitpatnaik)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestOptionInfo", propOrder = {"optionValueType", "optionValue", "_futureElements"})
public class RequestOptionInfo implements RequestOption {

    @XmlElement
    private String optionValueType;

    @XmlElement
    private String optionValue;

    @XmlAnyElement
    private List<Element> _futureElements;
        
    public RequestOptionInfo() {
        this.optionValue = null;
        this.optionValueType = null;
        this._futureElements = null;
    }
    
    public RequestOptionInfo(RequestOption reqOp) {
        if(null == reqOp) return;
        
        this.optionValue = reqOp.getOptionValue();
        this.optionValueType = reqOp.getOptionValueType();
        this._futureElements = null;
    }
    
    
    public void setOptionValueType(String optionValueType) {
        this.optionValueType = optionValueType;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    @Override
    public String getOptionValueType() {
        return optionValueType;
    }

    @Override
    public String getOptionValue() {
        return optionValue;
    }

}
