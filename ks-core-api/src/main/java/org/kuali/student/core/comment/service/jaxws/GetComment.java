
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

@XmlRootElement(name = "getComment", namespace = "http://student.kuali.org/wsdl/commentService")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getComment", namespace = "http://student.kuali.org/wsdl/commentService")

public class GetComment {

    @XmlElement(name = "commentId")
    private java.lang.String commentId;

    public java.lang.String getCommentId() {
        return this.commentId;
    }

    public void setCommentId(java.lang.String newCommentId)  {
        this.commentId = newCommentId;
    }

}

