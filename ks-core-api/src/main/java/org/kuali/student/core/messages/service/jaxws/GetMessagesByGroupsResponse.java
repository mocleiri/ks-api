
package org.kuali.student.core.messages.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.core.messages.dto.MessageList;

/**
 * This class was generated by Apache CXF 2.1.3
 * Fri Jan 09 10:52:53 PST 2009
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "getMessagesByGroupsResponse", namespace = "http://student.kuali.org/core/messages")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getMessagesByGroupsResponse", namespace = "http://student.kuali.org/core/messages")

public class GetMessagesByGroupsResponse {

    @XmlElement(name = "return")
    private MessageList _return;

    public MessageList getReturn() {
        return this._return;
    }

    public void setReturn(MessageList new_return)  {
        this._return = new_return;
    }

}

