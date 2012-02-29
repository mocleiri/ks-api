/*
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.common.infc.DateRange;
import org.w3c.dom.Element;

/**
 * A DTO for a date range.
 *
 * @author tom
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DateRangeInfo", propOrder = {
                "start", "end", "_futureElements"})

public class DateRangeInfo 
    implements DateRange, Serializable {
	
    private static final long serialVersionUID = 1L;

    @XmlElement
    private Date start;
    
    @XmlElement
    private Date end;
    
    @XmlAnyElement
    private List<Element> _futureElements;    
    

    /**
     *  Constructs a new DateRangeInfo.
     */
    public DateRangeInfo() {
    }
	
    /**
     * Constructs a new DateRangeInfo from another DateRange.
     *
     * @param dateRange the DateRange to copy
     */
    public DateRangeInfo(DateRange dateRange) {
        if (dateRange != null) {
            if (dateRange.getStart() != null) {
                this.start = new Date(dateRange.getStart().getTime());
            }

            if (dateRange.getEnd() != null) {
                this.end = new Date(dateRange.getEnd().getTime());
            }
	}
    }

    @Override
    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    @Override
    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
