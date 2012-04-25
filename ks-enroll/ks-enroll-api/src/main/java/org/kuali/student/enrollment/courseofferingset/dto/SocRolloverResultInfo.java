/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.enrollment.courseofferingset.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseofferingset.infc.SocRolloverResult;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SocInfo", propOrder = {"id",
    "typeKey",
    "stateKey",
    "sourceSocId",
    "targetSocId",
    "targetTermId",
    "optionKeys",
    "itemsProcessed",
    "itemsExpected",
    "message",
    "meta",
    "attributes",
    "_futureElements"})
public class SocRolloverResultInfo
        extends IdNamelessEntityInfo
        implements SocRolloverResult {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String sourceSocId;
    @XmlElement
    private String targetSocId;
    @XmlElement
    private String targetTermId;
    @XmlElement
    private List<String> optionKeys;
    @XmlElement
    private Integer itemsProcessed;
    @XmlElement
    private Integer itemsExpected;
    @XmlElement
    private RichTextInfo message;
    @XmlAnyElement
    private List<Element> _futureElements;

    public SocRolloverResultInfo() {
    }

    /**
     * Copies Soc rollover results
     *
     * @param 
     */
    public SocRolloverResultInfo(SocRolloverResult orig) {

        super(orig);

        if (orig == null) {
            return;
        }

        this.sourceSocId = orig.getSourceSocId();
        this.targetSocId = orig.getTargetSocId();
        this.targetTermId = orig.getTargetTermId();
        this.optionKeys = new ArrayList<String>(orig.getOptionKeys());
        this.itemsProcessed = orig.getItemsProcessed();
        this.itemsExpected = orig.getItemsExpected();
        if (orig.getMessage() != null) {
            this.message = new RichTextInfo (orig.getMessage());
        }
    }

    @Override
    public List<String> getOptionKeys() {
        if (this.optionKeys == null) {
            this.optionKeys = new ArrayList<String>();
        }
        return optionKeys;
    }

    public void setOptionKeys(List<String> optionKeys) {
        this.optionKeys = optionKeys;
    }

    @Override
    public String getSourceSocId() {
        return sourceSocId;
    }

    public void setSourceSocId(String sourceSocId) {
        this.sourceSocId = sourceSocId;
    }

    @Override
    public String getTargetSocId() {
        return targetSocId;
    }

    public void setTargetSocId(String targetSocId) {
        this.targetSocId = targetSocId;
    }

    @Override
    public String getTargetTermId() {
        return targetTermId;
    }

    public void setTargetTermId(String targetTermId) {
        this.targetTermId = targetTermId;
    }

    @Override
    public Integer getItemsProcessed() {
        return itemsProcessed;
    }

    public void setItemsProcessed(Integer itemsProcessed) {
        this.itemsProcessed = itemsProcessed;
    }

    @Override
    public Integer getItemsExpected() {
        return itemsExpected;
    }

    public void setItemsExpected(Integer itemsExpected) {
        this.itemsExpected = itemsExpected;
    }

    @Override
    public RichTextInfo getMessage() {
        return message;
    }

    public void setMessage(RichTextInfo message) {
        this.message = message;
    }

}
