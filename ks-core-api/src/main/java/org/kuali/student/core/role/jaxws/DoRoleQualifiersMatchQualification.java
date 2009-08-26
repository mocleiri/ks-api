
package org.kuali.student.core.role.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.ws.binding.AttributeSetAdapter;

/**
 * This class was generated by Apache CXF 2.1.3
 * Thu Nov 20 12:17:38 EST 2008
 * Generated source version: 2.1.3
 */

@XmlRootElement(name = "doRoleQualifiersMatchQualification", namespace = "http://student.kuali.org/wsdl/roleType")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "doRoleQualifiersMatchQualification", namespace = "http://student.kuali.org/wsdl/roleType", propOrder = {"arg0","arg1"})

public class DoRoleQualifiersMatchQualification {

    @XmlElement(name = "arg0")
    @XmlJavaTypeAdapter(AttributeSetAdapter.class)
    private org.kuali.rice.kim.bo.types.dto.AttributeSet arg0;
    @XmlElement(name = "arg1")
    @XmlJavaTypeAdapter(AttributeSetAdapter.class)
    private java.util.List<org.kuali.rice.kim.bo.types.dto.AttributeSet> arg1;

    public org.kuali.rice.kim.bo.types.dto.AttributeSet getArg0() {
        return this.arg0;
    }

    public void setArg0(org.kuali.rice.kim.bo.types.dto.AttributeSet newArg0)  {
        this.arg0 = newArg0;
    }

    public java.util.List<org.kuali.rice.kim.bo.types.dto.AttributeSet> getArg1() {
        return this.arg1;
    }

    public void setArg1(java.util.List<org.kuali.rice.kim.bo.types.dto.AttributeSet> newArg1)  {
        this.arg1 = newArg1;
    }

}

