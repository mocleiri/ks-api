
package org.kuali.student.core.dictionary.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.3
 * Fri Jan 16 15:32:21 EST 2009
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "validateObjectResponse", namespace = "http://org.kuali.student/dictonary")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateObjectResponse", namespace = "http://org.kuali.student/dictonary")

public class ValidateObjectResponse {

    @XmlElement(name = "return")
    private boolean _return;

    public boolean getReturn() {
        return this._return;
    }

    public void setReturn(boolean new_return)  {
        this._return = new_return;
    }

}

