
package org.kuali.student.core.messages.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.3
 * Fri Jan 09 10:52:53 PST 2009
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "getMessage", namespace = "http://student.kuali.org/core/messages")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getMessage", namespace = "http://student.kuali.org/core/messages", propOrder = {"localeKey","messageGroupKey","messageKey"})

public class GetMessage {

    @XmlElement(name = "localeKey")
    private java.lang.String localeKey;
    @XmlElement(name = "messageGroupKey")
    private java.lang.String messageGroupKey;
    @XmlElement(name = "messageKey")
    private java.lang.String messageKey;

    public java.lang.String getLocaleKey() {
        return this.localeKey;
    }

    public void setLocaleKey(java.lang.String newLocaleKey)  {
        this.localeKey = newLocaleKey;
    }

    public java.lang.String getMessageGroupKey() {
        return this.messageGroupKey;
    }

    public void setMessageGroupKey(java.lang.String newMessageGroupKey)  {
        this.messageGroupKey = newMessageGroupKey;
    }

    public java.lang.String getMessageKey() {
        return this.messageKey;
    }

    public void setMessageKey(java.lang.String newMessageKey)  {
        this.messageKey = newMessageKey;
    }

}

