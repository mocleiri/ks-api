
package org.kuali.student.core.comment.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Fri Jun 05 15:33:47 EDT 2009
 * Generated source version: 2.2
 */

@XmlRootElement(name = "addTag", namespace = "http://student.kuali.org/wsdl/commentService")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addTag", namespace = "http://student.kuali.org/wsdl/commentService", propOrder = {"referenceId","referenceTypeKey","tagInfo"})

public class AddTag {

    @XmlElement(name = "referenceId")
    private java.lang.String referenceId;
    @XmlElement(name = "referenceTypeKey")
    private java.lang.String referenceTypeKey;
    @XmlElement(name = "tagInfo")
    private org.kuali.student.core.comment.dto.TagInfo tagInfo;

    public java.lang.String getReferenceId() {
        return this.referenceId;
    }

    public void setReferenceId(java.lang.String newReferenceId)  {
        this.referenceId = newReferenceId;
    }

    public java.lang.String getReferenceTypeKey() {
        return this.referenceTypeKey;
    }

    public void setReferenceTypeKey(java.lang.String newReferenceTypeKey)  {
        this.referenceTypeKey = newReferenceTypeKey;
    }

    public org.kuali.student.core.comment.dto.TagInfo getTagInfo() {
        return this.tagInfo;
    }

    public void setTagInfo(org.kuali.student.core.comment.dto.TagInfo newTagInfo)  {
        this.tagInfo = newTagInfo;
    }

}

