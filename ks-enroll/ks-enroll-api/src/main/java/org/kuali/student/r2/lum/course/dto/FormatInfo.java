/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.r2.lum.course.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.common.dto.TimeAmountInfo;

import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.lum.course.infc.Format;

/**
 * 
 * 
 * @Author KSContractMojo
 * @Author Kamal
 * @Since Tue May 18 11:30:55 PDT 2010
 * @See <a href=
 *      "https://test.kuali.org/confluence/display/KULSTU/formatInfo+Structure"
 *      >FormatInfo</>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FormatInfo extends IdNamelessEntityInfo implements Format, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<ActivityInfo> activities;

    @XmlElement
    private List<String> termsOffered;

    @XmlElement
    private TimeAmountInfo duration;

    /**
     * 
     */
    @Override
    public List<ActivityInfo> getActivities() {
        if (activities == null) {
            activities = new ArrayList<ActivityInfo>(0);
        }
        return activities;
    }

    public void setActivities(List<ActivityInfo> activities) {
        this.activities = activities;
    }

    @Override
    public List<String> getTermsOffered() {
        return termsOffered;
    }

    public void setTermsOffered(List<String> termsOffered) {
        this.termsOffered = termsOffered;
    }

    @Override
    public TimeAmountInfo getDuration() {
        return duration;
    }

    public void setDuration(TimeAmountInfo duration) {
        this.duration = duration;
    }

   

}