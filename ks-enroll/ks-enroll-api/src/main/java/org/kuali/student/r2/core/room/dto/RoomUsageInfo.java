package org.kuali.student.r2.core.room.dto;

import org.kuali.student.r2.common.dto.HasAttributesAndMetaInfo;
import org.kuali.student.r2.core.room.infc.RoomUsage;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoomUsageInfo", propOrder = {"id", "usageTypeKey", "layoutTypeKey", "preferredCapacity", "hardCapacity", "examCapacity", "meta", "attributes", "_futureElements"})
public class RoomUsageInfo extends HasAttributesAndMetaInfo implements RoomUsage, Serializable {

    @XmlElement
    private String id;
    @XmlElement
    private String usageTypeKey;
    @XmlElement
    private String layoutTypeKey;
    @XmlElement
    private Integer preferredCapacity;
    @XmlElement
    private Integer hardCapacity;
    @XmlElement
    private Integer examCapacity;
    @XmlAnyElement
    private List<Element> _futureElements;


    public RoomUsageInfo() {

    }

    public RoomUsageInfo(RoomUsage roomUsage) {
        if (null != roomUsage) {
            this.preferredCapacity = roomUsage.getPreferredCapacity();
            this.hardCapacity = roomUsage.getHardCapacity();
            this.examCapacity = roomUsage.getExamCapacity();
            this.usageTypeKey = roomUsage.getUsageTypeKey();
            this.layoutTypeKey = roomUsage.getLayoutTypeKey();
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getUsageTypeKey() {
        return this.usageTypeKey;
    }

    public void setUsageTypeKey(String usageTypeKey) {
        this.usageTypeKey = usageTypeKey;
    }

    @Override
    public String getLayoutTypeKey() {
        return this.layoutTypeKey;
    }

    public void setLayoutTypeKey(String layoutTypeKey) {
        this.layoutTypeKey = layoutTypeKey;
    }
    
    @Override
    public Integer getPreferredCapacity() {
        return this.preferredCapacity;
    }

    public void setPreferredCapacity(Integer preferredCapacity) {
        this.preferredCapacity = preferredCapacity;
    }

    @Override
    public Integer getHardCapacity() {
        return this.hardCapacity;
    }

    public void setHardCapacity(Integer hardCapacity) {
        this.hardCapacity = hardCapacity;
    }

    @Override
    public Integer getExamCapacity() {
        return this.examCapacity;
    }

    public void setExamCapacity(Integer examCapacity) {
        this.examCapacity = examCapacity;
    }


}
